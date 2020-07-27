package pl.kmachuramika.minibank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kmachuramika.minibank.dtos.AmericanCurrencyDTO;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.mapper.AmericanCurrencyMapper;
import pl.kmachuramika.minibank.model.Currency;

@Slf4j
@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    private final AmericanCurrencyMapper americanCurrencyMapper;

    @Autowired
    public CurrencyService(RestTemplate restTemplate, AmericanCurrencyMapper americanCurrencyMapper) {
        this.restTemplate = restTemplate;
        this.americanCurrencyMapper = americanCurrencyMapper;
    }

    public Currency getUSDExchangeRate() {
       Currency currency = americanCurrencyMapper.mapToCurrency(restTemplate.getForObject(
                        "http://api.nbp.pl/api/exchangerates/rates/A/" + CurrencyShortcutEnum.USD.toString() +"/", AmericanCurrencyDTO.class));
                log.info(currency.toString());
        return currency;
    }

}
