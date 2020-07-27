package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.AccountDTO;
import pl.kmachuramika.minibank.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account mapToAccount(AccountDTO accountDTO);

    AccountDTO mapToAccountDTO(Account account);

}
