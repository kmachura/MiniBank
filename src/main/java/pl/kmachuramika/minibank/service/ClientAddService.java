package pl.kmachuramika.minibank.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.dtos.SubaccountDTO;
import pl.kmachuramika.minibank.enums.CurrencyNameEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.exception.ClientAlreadyExistsException;
import pl.kmachuramika.minibank.exception.ClientNotFoundException;
import pl.kmachuramika.minibank.exception.ClientNotHaveSubaccountForThisCurrency;
import pl.kmachuramika.minibank.exception.ClientsNotFoundException;
import pl.kmachuramika.minibank.exception.PeselCannotBeNullException;
import pl.kmachuramika.minibank.exception.TooYoungException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.EUR;
import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.PLN;
import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.USD;

@Slf4j
@Service
@AllArgsConstructor
public class ClientAddService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public Client addClient(ClientDTO clientDTO) {
        handlePesel(clientDTO.getPesel());
        if(clientRepository.existsByPesel(clientDTO.getPesel())) {
            throw new ClientAlreadyExistsException("Client with given Pesel number already has an account");
        } else {
            Client client = clientMapper.mapToClient(clientDTO);
            refillClient(client);
            return clientRepository.save(client);
        }
    }

    private void refillClient(Client client) {
        List<Currency> subaccountCurrencies = client.getSubAccounts().stream().map(Account::getCurrency).collect(Collectors.toList());
        refillCurrencies(subaccountCurrencies);
        Currency primaryCurrency = new Currency();
        primaryCurrency.setName(CurrencyNameEnum.PLN.getName());
        primaryCurrency.setShortcut(PLN);
        client.getPrimaryAccount().setCurrency(primaryCurrency);
    }

    private void refillCurrencies(List<Currency> currencies) {
        for (Currency currency : currencies) {
            if (currency.getShortcut().equals(PLN)) {
                currency.setName(CurrencyNameEnum.PLN.getName());
            } else if (currency.getShortcut().equals(USD)) {
                currency.setName(CurrencyNameEnum.USD.getName());
            } else {
                currency.setName(CurrencyNameEnum.EUR.getName());
            }
        }
    }

    private void handlePesel(String pesel) {
        Optional<String> peselOpt = Optional.ofNullable(pesel);
        if (peselOpt.isEmpty()) {
            throw new PeselCannotBeNullException("PESEL cannot be null");
        } else {
            establishAge(peselOpt.get());
        }
    }

    private void establishAge(String pesel) {
        int age = peselToAge(pesel);
        if (age < 18) {
            throw new TooYoungException("To create account, client must be minimum 18 years old");
        }
    }

    private static int peselToAge(String pesel) {
        int day = Integer.parseInt(pesel.substring(4, 6));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int year = Integer.parseInt(pesel.substring(0, 2));
        Integer thirdNumber = Integer.valueOf(pesel.substring(2, 3));
        if (thirdNumber.equals(0) | thirdNumber.equals(1)) {
            year = year + 1900;
        } else if (thirdNumber.equals(2) | thirdNumber.equals(3)) {
            year = year + 2000;
            month = month - 20;
        }

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

}
