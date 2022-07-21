package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kmachuramika.minibank.dtos.AccountDTO;
import pl.kmachuramika.minibank.model.Account;

@Mapper(uses={CurrencyMapper.class}, componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "currencyShortcutEnum", target = "currency.shortcut")
    Account mapToAccount(AccountDTO accountDTO);

    AccountDTO mapToAccountDTO(Account account);

}
