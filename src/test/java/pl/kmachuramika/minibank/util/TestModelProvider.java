package pl.kmachuramika.minibank.util;

import pl.kmachuramika.minibank.enums.AccountTypeEnum;
import pl.kmachuramika.minibank.enums.CurrencyNameEnum;
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
        primaryAccount.setAccountBalance(BigDecimal.valueOf(1000.00));
        primaryAccount.setAccountNumber("PL12347467868712312312312423");
        primaryAccount.setAccountType(AccountTypeEnum.PRIMARY);

        Currency currency = new Currency();
        currency.setName(CurrencyNameEnum.PLN.getName());
        currency.setShortcut(CurrencyShortcutEnum.PLN);

        primaryAccount.setCurrency(currency);

        client.setPrimaryAccount(primaryAccount);
        return client;
    }
}
