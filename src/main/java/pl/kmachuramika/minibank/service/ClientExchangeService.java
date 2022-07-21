package pl.kmachuramika.minibank.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.exception.ClientNotFoundException;
import pl.kmachuramika.minibank.exception.ClientNotHaveSubaccountForThisCurrency;
import pl.kmachuramika.minibank.mapper.ClientMapper;
import pl.kmachuramika.minibank.model.Account;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.model.Rate;
import pl.kmachuramika.minibank.repository.ClientRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.PLN;
import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.USD;

@Slf4j
@Service
@AllArgsConstructor
public class ClientExchangeService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final CurrencyService currencyService;

    public ClientDTO exchangeMoney(CurrencyShortcutEnum fromCurrency, CurrencyShortcutEnum toCurrency, String pesel, BigDecimal amountToChange) {
        Client client = Optional.ofNullable(clientRepository.findClientByPesel(pesel))
                .orElseThrow(() -> new ClientNotFoundException("Client with given pesel doesn't exist"));

        if (checkIfClientHaveCurrencySubaccount(client, fromCurrency, toCurrency)) {
            throw new ClientNotHaveSubaccountForThisCurrency("This client doesn't have proper subaccount for exchange in these currencies");
        }

        Client forSave = performExchange(client, fromCurrency, toCurrency, amountToChange);
        clientRepository.save(forSave);
        return clientMapper.mapToClientDTO(forSave);
    }

    private Client performExchange(Client client, CurrencyShortcutEnum fromCurrencyShortcut, CurrencyShortcutEnum toCurrencyShortcut, BigDecimal amountToChange) {
        if (!fromCurrencyShortcut.equals(PLN) && toCurrencyShortcut.equals(PLN)) {
            exchangeFromForeignToPLN(client, fromCurrencyShortcut, amountToChange);
        } else if (!fromCurrencyShortcut.equals(PLN)) {
            exchangeForeignToForeign(client, fromCurrencyShortcut, toCurrencyShortcut, amountToChange);
        } else {
            exchangePLNToForeign(client, toCurrencyShortcut, amountToChange);
        }
        return client;
    }

    private void exchangePLNToForeign(Client client, CurrencyShortcutEnum toCurrencyShortcut, BigDecimal amountToChange) {
        Account accountAfterExchange;
        List<Rate> ratesList = getRatesBasedOnCurrency(toCurrencyShortcut);
        accountAfterExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(toCurrencyShortcut))
                .findAny().orElseThrow(()-> new ClientNotHaveSubaccountForThisCurrency("This client doesn't have proper subaccount for exchange in these currency"));
        accountAfterExchange.setAccountBalance(accountAfterExchange.getAccountBalance().add(amountToChange.divide(BigDecimal.valueOf(ratesList.get(0).getMidRate()), RoundingMode.HALF_UP)));
        client.getPrimaryAccount().setAccountBalance(client.getPrimaryAccount().getAccountBalance().subtract(amountToChange));
    }

    private void exchangeForeignToForeign(Client client, CurrencyShortcutEnum fromCurrencyShortcut, CurrencyShortcutEnum toCurrencyShortcut, BigDecimal amountToChange) {
        Account accountAfterExchange;
        Account accountForExchange;
        List<Rate> ratesList = getRatesBasedOnCurrency(fromCurrencyShortcut);
        List<Rate> toRatesList = getRatesBasedOnCurrency(toCurrencyShortcut);
        BigDecimal amountToPLN = amountToChange.multiply(BigDecimal.valueOf(ratesList.get(0).getMidRate()));
        BigDecimal amountToExchangedCurrency = amountToPLN.divide(BigDecimal.valueOf(toRatesList.get(0).getMidRate()), RoundingMode.HALF_UP);
        accountAfterExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(toCurrencyShortcut))
                .findAny().orElseThrow(()-> new ClientNotHaveSubaccountForThisCurrency("This client doesn't have proper subaccount for exchange in these currency"));
        accountAfterExchange.setAccountBalance(accountAfterExchange.getAccountBalance().add(amountToExchangedCurrency));
        accountForExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(fromCurrencyShortcut))
                .findAny().orElseThrow(()-> new ClientNotHaveSubaccountForThisCurrency("This client doesn't have proper subaccount for exchange in these currency"));
        accountForExchange.setAccountBalance(accountForExchange.getAccountBalance().subtract(amountToChange));
    }

    private void exchangeFromForeignToPLN(Client client, CurrencyShortcutEnum fromCurrencyShortcut, BigDecimal amountToChange) {
        Account accountAfterExchange;
        Account accountForExchange;
        List<Rate> ratesList = getRatesBasedOnCurrency(fromCurrencyShortcut);
        accountAfterExchange = client.getPrimaryAccount();
        accountAfterExchange.setAccountBalance(accountAfterExchange.getAccountBalance().add((amountToChange.multiply(BigDecimal.valueOf(ratesList.get(0).getMidRate())))));
        accountForExchange = client.getSubAccounts().stream().filter(account -> account.getCurrency().getShortcut().equals(fromCurrencyShortcut))
                .findAny().orElseThrow(()-> new ClientNotHaveSubaccountForThisCurrency("This client doesn't have proper subaccount for exchange in these currency"));
        accountForExchange.setAccountBalance(accountForExchange.getAccountBalance().subtract(amountToChange));
    }

    private List<Rate> getRatesBasedOnCurrency(CurrencyShortcutEnum currencyShortcut) {
        if (currencyShortcut.equals(USD)) {
            return currencyService.getUSDExchangeRate().getRates();
        } else {
            return currencyService.getEURExchangeRate().getRates();
        }
    }

    private boolean checkIfClientHaveCurrencySubaccount(Client client, CurrencyShortcutEnum fromCurrency, CurrencyShortcutEnum toCurrency) {
        List<Boolean> accounts = new ArrayList<>();
        if (!fromCurrency.equals(PLN)) {
            Optional<Account> fromCurrencySubaccountOpt = client.getSubAccounts().stream().filter(x -> x.getCurrency().getShortcut().equals(fromCurrency)).findFirst();
            fromCurrencySubaccountOpt.ifPresent(x -> accounts.add(Boolean.TRUE));
        }
        if (!toCurrency.equals(PLN)) {
            Optional<Account> toCurrencySubaccountOpt = client.getSubAccounts().stream().filter(x -> x.getCurrency().getShortcut().equals(toCurrency)).findFirst();
            toCurrencySubaccountOpt.ifPresent(x -> accounts.add(Boolean.TRUE));
        }
        return accounts.contains(Boolean.FALSE);
    }
}
