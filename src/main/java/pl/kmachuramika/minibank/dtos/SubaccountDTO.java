package pl.kmachuramika.minibank.dtos;

import lombok.Data;
import pl.kmachuramika.minibank.enums.AccountTypeEnum;

import java.math.BigDecimal;

@Data
public class SubaccountDTO {

    private Long accountNumber;

    private AccountTypeEnum accountType = AccountTypeEnum.SUBACCOUNT;

    private BigDecimal accountBalance;

    private CurrencyDTO currency;

}
