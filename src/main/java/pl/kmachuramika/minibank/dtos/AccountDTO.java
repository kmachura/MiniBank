package pl.kmachuramika.minibank.dtos;

import lombok.Data;
import pl.kmachuramika.minibank.enums.AccountTypeEnum;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    private Long accountNumber;

    private AccountTypeEnum accountType = AccountTypeEnum.PRIMARY;

    private BigDecimal accountBalance;

    private PolishCurrencyDTO currency;

}
