package pl.kmachuramika.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kmachuramika.minibank.model.Client;

import java.util.List;
import java.util.UUID;


@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findClientByPesel(String pesel);

    List<Client> findAll();

    Client save(Client client);

    boolean existsByPesel(String pesel);

}
