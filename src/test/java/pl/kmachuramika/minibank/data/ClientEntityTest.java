package pl.kmachuramika.minibank.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.kmachuramika.minibank.model.Client;
import pl.kmachuramika.minibank.repository.ClientRepository;

import static pl.kmachuramika.minibank.util.TestModelProvider.getClient;

@DataJpaTest
@Slf4j
public class ClientEntityTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void clientRepositoryIsLoaded() {
        Assertions.assertNotNull(clientRepository);
    }

    @Test
    @DisplayName("Test is checking, if we are able to create Client Entity with all subentities and then check findByPesel method")
    void findClientById() {
        //given
        Client client = getClient();
        clientRepository.save(client);

        //when
        Client foundedClient = clientRepository.findClientByPesel(client.getPesel());

        //then
        log.info("Testing saving & finding client by pesel");
        Assertions.assertNotNull(foundedClient);
        Assertions.assertEquals(client, foundedClient);

    }

}
