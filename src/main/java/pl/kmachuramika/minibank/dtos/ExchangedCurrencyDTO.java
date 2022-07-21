package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExchangedCurrencyDTO {

    @JsonProperty("currency")
    private String name;

    @JsonProperty("code")
    private String shortcut;

    @JsonProperty("rates")
    private List<RateDTO> rates;

}
