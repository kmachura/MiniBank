package pl.kmachuramika.minibank.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.dtos.SubaccountDTO;
import pl.kmachuramika.minibank.enums.CurrencyNameEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.exception.ClientAlreadyExistsException;
import pl.kmachuramika.minibank.exception.ClientNotFoundException;
import pl.kmachuramika.minibank.exception.ClientNotHaveSubaccountForThisCurrency;
import pl.kmachuramika.minibank.exception.ClientsNotFoundException;
import pl.kmachuramika.minibank.exception.PeselCannotBeNullException;
import pl.kmachuramika.minibank.mapper.ClientMapper;
import pl.kmachuramika.minibank.mapper.SubaccountMapper;
import pl.kmachuramika.minibank.model.Account;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.model.Currency;
import pl.kmachuramika.minibank.model.Rate;
import pl.kmachuramika.minibank.repository.ClientRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.EUR;
import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.PLN;
import static pl.kmachuramika.minibank.enums.CurrencyShortcutEnum.USD;

@Slf4j
@Service
@AllArgsConstructor
public class ClientSearchService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public List<ClientDTO> findAll() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> dtoList;
        if (CollectionUtils.isEmpty(clients)) {
            throw new ClientsNotFoundException("The clients' list is empty");
        } else {
            dtoList = clientMapper.mapToClientsDTO(clients);
        }
        return dtoList;
    }

    public ClientDTO findClientByPesel(String pesel) {
        return Optional.ofNullable(clientMapper.mapToClientDTO(clientRepository.findClientByPesel(pesel)))
                .orElseThrow(() -> new ClientNotFoundException("Client with given pesel doesn't exist"));
    }
}
