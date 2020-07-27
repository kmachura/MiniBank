package pl.kmachuramika.minibank.util;

import pl.kmachuramika.minibank.enums.AccountTypeEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.model.Account;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.model.Currency;

import java.math.BigDecimal;

public class TestModelProvider {

    public static Client getClient() {
        Client client = new Client();
        client.setFirstName("John");
        client.setSurname("Doe");
        client.setEmail("john@doe.pl");
        client.setPesel("79040712257");
        client.setPhoneNumber("123-123-123");

        Account primaryAccount = new Account();
        primaryAccount.setAccountBalance(new BigDecimal(1000.00));
        primaryAccount.setAccountNumber(123474678687l);
        primaryAccount.setAccountType(AccountTypeEnum.PRIMARY);

        Currency currency = new Currency();
        currency.setName("Polish Zloty");
        currency.setShortcut(CurrencyShortcutEnum.PLN.toString());

        primaryAccount.setCurrency(currency);

        client.setPrimaryAccount(primaryAccount);
        return client;
    }
}
