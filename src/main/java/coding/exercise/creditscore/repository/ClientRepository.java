package coding.exercise.creditscore.repository;

import coding.exercise.creditscore.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
