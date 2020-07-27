package pl.kmachuramika.minibank.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.kmachuramika.minibank.service.ClientService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Api(tags = "Clients", produces = "application/json", consumes = "application/json")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @ApiOperation(value = "Find all clients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information was found."),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping(value = "/{pesel}")
    @ApiOperation(value = "Find client by pesel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information was found."),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ResponseEntity<ClientDTO> findClientByPesel(@PathVariable("pesel") String pesel) {
        return ResponseEntity.ok(clientService.findClientByPesel(pesel));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "Add new client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client was created."),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Client with given PESEL number already has an account."),
    })
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.addClient(clientDTO));
    }

    @PatchMapping(value = "/addSubaccount")
    @ApiOperation(value = "Add subaccount for an existing client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Subaccount was added."),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Client with given PESEL number doesn't exist."),
    })
    public ResponseEntity<Client> addSubaccount(@RequestBody SubaccountDTO subaccountDTO, String pesel) {
        return ResponseEntity.ok(clientService.addSubaccount(subaccountDTO, pesel));
    }

    @PatchMapping("/exchangeMoney")
    @ApiOperation(value = "Exchange money. Choose the currency to be exchanged and the right amount. After exchange appropriate account balance will be changed.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Money was exchanged"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Client and accounts not found")
    })
    public ResponseEntity<ClientDTO> exchangeMoney(CurrencyShortcutEnum currencyShortcutEnum, String pesel, BigDecimal amountToChange) {
        return ResponseEntity.ok(clientService.exchangeMoney(currencyShortcutEnum, pesel, amountToChange));
    }

}

