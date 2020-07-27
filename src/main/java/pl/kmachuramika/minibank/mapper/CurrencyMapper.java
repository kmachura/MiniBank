package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.CurrencyDTO;
import pl.kmachuramika.minibank.model.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency mapToCurrency(CurrencyDTO currencyDTO);

    CurrencyDTO mapToCurrencyDTO(Currency currency);

}
