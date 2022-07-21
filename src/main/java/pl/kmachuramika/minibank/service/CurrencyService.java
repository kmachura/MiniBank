package pl.kmachuramika.minibank.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kmachuramika.minibank.dtos.ExchangedCurrencyDTO;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.mapper.AmericanCurrencyMapper;
import pl.kmachuramika.minibank.mapper.EuroCurrencyMapper;
import pl.kmachuramika.minibank.model.Currency;

@Slf4j
@Service
@AllArgsConstructor
public class CurrencyService {

    private final RestTemplate restTemplate;
    private final AmericanCurrencyMapper americanCurrencyMapper;
    private final EuroCurrencyMapper euroCurrencyMapper;

    public Currency getUSDExchangeRate() {
       Currency currency = americanCurrencyMapper.mapToCurrency(restTemplate.getForObject(
                        "http://api.nbp.pl/api/exchangerates/rates/A/" + CurrencyShortcutEnum.USD +"/", ExchangedCurrencyDTO.class));
                log.info(currency.toString());
        return currency;
    }

    public Currency getEURExchangeRate() {
        Currency currency = euroCurrencyMapper.mapToCurrency(restTemplate.getForObject(
                "http://api.nbp.pl/api/exchangerates/rates/A/" + CurrencyShortcutEnum.EUR +"/", ExchangedCurrencyDTO.class));
        log.info(currency.toString());
        return currency;
    }

}
