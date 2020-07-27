package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AmericanCurrencyDTO {

    @JsonProperty("currency")
    private String name;

    @JsonProperty("code")
    private String shortcut;

    @JsonProperty("rates")
    private RateDTO[] rates;

}
