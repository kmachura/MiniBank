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
public class ClientUpdateService {

    private final ClientRepository clientRepository;
    private final SubaccountMapper subaccountMapper;

    public Client addSubaccount(SubaccountDTO subaccountDTO, String pesel) {
        Client client = Optional.ofNullable(clientRepository.findClientByPesel(pesel))
                .orElseThrow(() -> new ClientNotFoundException("Client with given pesel doesn't exist"));
        List<Account> subaccountsList = client.getSubAccounts();
        Optional<Account> accountOpt = subaccountsList.stream().filter(account -> account.getCurrency().getShortcut().equals(subaccountDTO.getCurrencyShortcutEnum())).findAny();
        if (accountOpt.isPresent()) {
            throw new ClientAlreadyExistsException("This client already has subaccount in " + subaccountDTO.getCurrencyShortcutEnum().toString());
        }
        Account subaccount = subaccountMapper.mapToAccount(subaccountDTO);
        Currency currency = subaccount.getCurrency();
        currency.setName(CurrencyNameEnum.valueOf(currency.getShortcut().toString()).getName());
        subaccountsList.add(subaccount);
        client.setSubAccounts(subaccountsList);
        return clientRepository.save(client);
    }
}
