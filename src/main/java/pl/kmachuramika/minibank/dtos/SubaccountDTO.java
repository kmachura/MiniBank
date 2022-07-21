package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import pl.kmachuramika.minibank.enums.AccountTypeEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;

import java.math.BigDecimal;

@Data
public class SubaccountDTO {

    @JsonIgnore
    private String accountNumber = "PL" + RandomStringUtils.randomNumeric(26);

    @Schema(defaultValue = "SUBACCOUNT")
    private AccountTypeEnum accountType;

    private BigDecimal accountBalance;

    @Schema(defaultValue = "USD")
    private CurrencyShortcutEnum currencyShortcutEnum;

}
