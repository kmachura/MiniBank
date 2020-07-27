package pl.kmachuramika.minibank.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.model.Currency;
import pl.kmachuramika.minibank.service.CurrencyService;

@RestController
@RequestMapping("/api/currency")
@Api(tags = "Currencies", produces = "application/json", consumes = "application/json")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/exchangeRate")
    @ApiOperation(value = "Find exchange rate for USD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information was found."),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<Currency> findExchangeRateForUSD() {
        return ResponseEntity.ok(currencyService.getUSDExchangeRate());
    }

}
