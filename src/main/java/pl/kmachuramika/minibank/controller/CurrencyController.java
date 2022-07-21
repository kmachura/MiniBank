package pl.kmachuramika.minibank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kmachuramika.minibank.model.Currency;
import pl.kmachuramika.minibank.service.CurrencyService;

@RestController
@RequestMapping("/api/currency")
@Tag(name = "Currencies")
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/exchangeRateUSD")
    @Operation(summary = "Find exchange rate for USD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information was found."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Currency> findExchangeRateForUSD() {
        return ResponseEntity.ok(currencyService.getUSDExchangeRate());
    }

    @GetMapping("/exchangeRateEUR")
    @Operation(summary = "Find exchange rate for EUR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information was found."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Currency> findExchangeRateForEUR() {
        return ResponseEntity.ok(currencyService.getEURExchangeRate());
    }

}
