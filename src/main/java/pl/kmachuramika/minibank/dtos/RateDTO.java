package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RateDTO {

    @JsonProperty("mid")
    private double midRate;

    @JsonProperty("effectiveDate")
    private String date;

}
