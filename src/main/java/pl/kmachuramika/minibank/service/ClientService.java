package pl.kmachuramika.minibank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.dtos.SubaccountDTO;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.mapper.ClientMapper;
import pl.kmachuramika.minibank.mapper.SubaccountMapper;
import pl.kmachuramika.minibank.model.Account;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.model.Currency;
import pl.kmachuramika.minibank.model.Rate;
import pl.kmachuramika.minibank.repository.ClientRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.USD;

@Slf4j
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final SubaccountMapper subaccountMapper;

    private final CurrencyService currencyService;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, SubaccountMapper subaccountMapper, CurrencyService currencyService) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.subaccountMapper = subaccountMapper;
        this.currencyService = currencyService;
    }

    public List<ClientDTO> findAll() {
        return Optional.ofNullable(clientMapper.mapToClientsDTO(clientRepository.findAll()))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "The list of clients is empty"));
    }

    public ClientDTO findClientByPesel(String pesel) {
        return Optional.ofNullable(clientMapper.mapToClientDTO(clientRepository.findClientByPesel(pesel)))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client with given pesel doesn't exist"));
    }


    public Client addClient(ClientDTO clientDTO) {
        if(clientRepository.existsById(clientDTO.getPesel())) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Client with given Pesel number already has an account");
        } else {
            String pesel = clientDTO.getPesel();
            int age = peselToAge(pesel);
            if (age < 18) {
                throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "To create account, client must be minimum 18 years old");
            } else {
                return clientRepository.save(clientMapper.mapToClient(clientDTO));
            }
        }
    }

    public Client addSubaccount(SubaccountDTO subaccountDTO, String pesel) {
        Client client = Optional.ofNullable(clientRepository.findClientByPesel(pesel))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client with given pesel doesn't exist"));
        List<Account> subaccountsList = client.getSubAccounts();
        subaccountsList.add(subaccountMapper.mapToAccount(subaccountDTO));
        client.setSubAccounts(subaccountsList);
        return clientRepository.save(client);
    }

    public ClientDTO exchangeMoney(CurrencyShortcutEnum currencyShortcutEnum, String pesel, BigDecimal amountToChange) {
        Client client = Optional.ofNullable(clientRepository.findClientByPesel(pesel))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Client with given pesel doesn't exist"));
        Account accountForExchange = new Account();
        Account accountAfterExchange;
        Currency currency = currencyService.getUSDExchangeRate();
        List<Rate> ratesList = Arrays.asList(currency.getRates());
        if (currencyShortcutEnum.equals(USD)) {
            accountForExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(currencyShortcutEnum.toString()))
                    .findAny().orElseThrow(()-> new IllegalArgumentException());
            accountAfterExchange = client.getPrimaryAccount();
            accountAfterExchange.setAccountBalance(accountAfterExchange.getAccountBalance().add((amountToChange.multiply(BigDecimal.valueOf(ratesList.get(0).getMidRate())))));
        } else if (currencyShortcutEnum.equals(currencyShortcutEnum.PLN)) {
            accountForExchange = client.getPrimaryAccount();
            accountAfterExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(CurrencyShortcutEnum.USD.toString()))
                    .findAny().orElseThrow(()-> new IllegalArgumentException());
            accountAfterExchange.setAccountBalance(accountAfterExchange.getAccountBalance().add(amountToChange.divide(BigDecimal.valueOf(ratesList.get(0).getMidRate()), RoundingMode.HALF_UP)));
        }
        accountForExchange.setAccountBalance(accountForExchange.getAccountBalance().subtract(amountToChange));
        clientRepository.save(client);
        return clientMapper.mapToClientDTO(client);
    }

    private static int peselToAge(String pesel) {
        Integer day = Integer.valueOf(pesel.substring(4, 6));
        Integer month = Integer.valueOf(pesel.substring(2, 4));
        Integer year = Integer.valueOf(pesel.substring(0, 2));
        Integer thirdNumber = Integer.valueOf(pesel.substring(2, 3));
        if (thirdNumber.equals(0) | thirdNumber.equals(1)) {
            year = year + 1900;
        } else if (thirdNumber.equals(2) | thirdNumber.equals(3)) {
            year = year + 2000;
            month = month - 20;
        }

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();

        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

}
