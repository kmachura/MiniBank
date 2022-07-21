package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.ExchangedCurrencyDTO;
import pl.kmachuramika.minibank.model.Currency;

@Mapper(componentModel = "spring")
public interface EuroCurrencyMapper {

    Currency mapToCurrency(ExchangedCurrencyDTO euroCurrencyDTO);

}
