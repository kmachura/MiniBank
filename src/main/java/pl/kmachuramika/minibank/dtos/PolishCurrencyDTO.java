package pl.kmachuramika.minibank.dtos;

import lombok.Data;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;

@Data
public class PolishCurrencyDTO {

    private String name = "Polish Zloty";

    private String shortcut = CurrencyShortcutEnum.PLN.toString();

}
