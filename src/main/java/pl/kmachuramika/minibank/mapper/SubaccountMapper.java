package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kmachuramika.minibank.dtos.SubaccountDTO;
import pl.kmachuramika.minibank.model.Account;

import java.util.List;

@Mapper(uses={AccountMapper.class, CurrencyMapper.class}, componentModel = "spring")
public interface SubaccountMapper {

    Account mapToAccount(SubaccountDTO subaccountDTO);

    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountBalance", target = "accountBalance")
    @Mapping(source = "currency", target = "currency")
    SubaccountDTO mapToSubaccountDTO(Account account);

    List<Account> mapToAccounts(List<SubaccountDTO> subaccountDTOList);

    List<SubaccountDTO> mapToSubaccountsDTO(List<Account> accountList);

}
