package pl.kmachuramika.minibank.mapper;

import org.mapstruct.Mapper;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.model.Client;

import java.util.List;

@Mapper(uses={SubaccountMapper.class, AccountMapper.class}, componentModel = "spring")
public interface ClientMapper {

    Client mapToClient(ClientDTO clientDTO);

    ClientDTO mapToClientDTO(Client client);

    List<Client> mapToClients(List<ClientDTO> clientDTOList);

    List<ClientDTO> mapToClientsDTO(List<Client> clientList);

}
