package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.PolishCurrencyDTO;
import pl.kmachuramika.minibank.model.Currency;

@Mapper(componentModel = "spring")
public interface PolishCurrencyMapper {

    Currency mapToCurrency(PolishCurrencyDTO polishCurrencyDTO);

    PolishCurrencyDTO mapToPolishCurrencyDTO(Currency currency);

}
