package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import pl.kmachuramika.minibank.enums.AccountTypeEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @JsonIgnore
    private UUID id = UUID.randomUUID();

    @JsonIgnore
    private String accountNumber = "PL" + RandomStringUtils.randomNumeric(26);

    private AccountTypeEnum accountType = AccountTypeEnum.PRIMARY;

    private BigDecimal accountBalance;

    @Schema(defaultValue = "PLN")
    private CurrencyShortcutEnum currencyShortcutEnum;

}
