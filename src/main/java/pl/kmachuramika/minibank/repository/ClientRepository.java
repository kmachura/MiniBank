package pl.kmachuramika.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kmachuramika.minibank.model.Client;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findClientByPesel(String pesel);

    List<Client> findAll();

    Client save(Client client);

}
