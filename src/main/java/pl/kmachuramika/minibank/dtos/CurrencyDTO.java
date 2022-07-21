package pl.kmachuramika.minibank.dtos;

import lombok.Data;
import pl.kmachuramika.minibank.enums.CurrencyNameEnum;

@Data
public class CurrencyDTO {

    private CurrencyNameEnum nameEnum;

    private String shortcut;

    private RateDTO[] rates;

}
