package pl.kmachuramika.minibank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kmachuramika.minibank.dtos.ClientDTO;
import pl.kmachuramika.minibank.dtos.SubaccountDTO;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.service.ClientAddService;
import pl.kmachuramika.minibank.service.ClientSearchService;
import pl.kmachuramika.minibank.service.ClientExchangeService;
import pl.kmachuramika.minibank.service.ClientUpdateService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name="Clients")
@AllArgsConstructor
public class ClientController {

    private final ClientExchangeService clientExchangeService;
    private final ClientAddService clientAddService;
    private final ClientSearchService clientSearchService;
    private final ClientUpdateService clientUpdateService;

    @GetMapping
    @Operation(summary= "Find all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information was found."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok(clientSearchService.findAll());
    }

    @GetMapping(value = "/{pesel}")
    @Operation(summary = "Find client by pesel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information was found."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ClientDTO> findClientByPesel(@PathVariable("pesel") String pesel) {
        return ResponseEntity.ok(clientSearchService.findClientByPesel(pesel));
    }

    @PostMapping(value = "/add")
    @Operation(summary = "Add new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client was created."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Client with given PESEL number already has an account."),
    })
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientAddService.addClient(clientDTO));
    }

    @PatchMapping(value = "/addSubaccount")
    @Operation(summary = "Add subaccount for an existing client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subaccount was added."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client with given PESEL number doesn't exist."),
    })
    public ResponseEntity<Client> addSubaccount(@RequestBody SubaccountDTO subaccountDTO, String pesel) {
        return ResponseEntity.ok(clientUpdateService.addSubaccount(subaccountDTO, pesel));
    }

    @PatchMapping("/exchangeMoney")
    @Operation(summary = "Exchange money. Choose the currency to be exchanged and the right amount. After exchange appropriate account balance will be changed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money was exchanged"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client and accounts not found")
    })
    public ResponseEntity<ClientDTO> exchangeMoney(CurrencyShortcutEnum fromCurrency, CurrencyShortcutEnum toCurrency, String pesel, BigDecimal amountToChange) {
        return ResponseEntity.ok(clientExchangeService.exchangeMoney(fromCurrency, toCurrency, pesel, amountToChange));
    }

}

