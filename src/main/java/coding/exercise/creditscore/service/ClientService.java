package coding.exercise.creditscore.service;

import coding.exercise.creditscore.model.Client;

import java.math.BigDecimal;


public interface ClientService {

    Client findById(long id);

    Iterable<Client> findAll();

    Long save(Client client);

    void setClientIncome(Long id, BigDecimal income);

    Long getClientCreditScore(Long id);

}
