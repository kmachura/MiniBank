package pl.kmachuramika.minibank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.kmachuramika.minibank.mapper.ClientMapper;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.service.ClientAddService;
import pl.kmachuramika.minibank.service.ClientSearchService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.kmachuramika.minibank.util.TestModelProvider.getClient;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientAddService clientAddServiceMock;

    @MockBean
    private ClientSearchService clientSearchServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Test
    @DisplayName("Test is checking, if we will get 200 Status with proper RequestBody")
    void addingClientWithSubentitiesViaPOSTTest() throws Exception {
        //given
        Client client = getClient();
        String requestBody =  objectMapper.writeValueAsString(client);

        //when
        when(clientAddServiceMock.addClient(clientMapper.mapToClientDTO(client))).thenReturn(new Client());

        //then
        mockMvc.perform(post("/api/clients/add")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Test is checking, if we will get status 400 with missing RequestBody")
    void addingClientWithSubentitiesViaPOSTWithoutRequestBodyTest() throws Exception {
        mockMvc.perform(post("/api/clients/add")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test is checking, if we will get status 200 with valid PESEL using GET method for finding Client")
    void getClientByPeselViaGETTest() throws Exception {
        //given
        String pesel = "79040712257";
        Client client = getClient();

        //when
        when(clientSearchServiceMock.findClientByPesel(pesel)).thenReturn(clientMapper.mapToClientDTO(client));

        //then
        mockMvc.perform(get("/api/clients/" + pesel)
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
