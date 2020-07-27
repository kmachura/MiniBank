package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.AmericanCurrencyDTO;
import pl.kmachuramika.minibank.model.Currency;

@Mapper(componentModel = "spring")
public interface AmericanCurrencyMapper {

    Currency mapToCurrency(AmericanCurrencyDTO americanCurrencyDTO);

    AmericanCurrencyDTO mapToAmericanCurrencyDTO(Currency currency);

}
