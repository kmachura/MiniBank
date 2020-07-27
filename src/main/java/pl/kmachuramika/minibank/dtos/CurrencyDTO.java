package pl.kmachuramika.minibank.dtos;

import lombok.Data;

@Data
public class CurrencyDTO {

    private String name;

    private String shortcut;

    private RateDTO[] rates;

}
