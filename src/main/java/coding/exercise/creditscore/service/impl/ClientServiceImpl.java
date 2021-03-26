package coding.exercise.creditscore.service.impl;

import coding.exercise.creditscore.exception.ClientNotFoundException;
import coding.exercise.creditscore.exception.NoCreditScoreException;
import coding.exercise.creditscore.model.Calculation;
import coding.exercise.creditscore.model.Client;
import coding.exercise.creditscore.repository.ClientRepository;
import coding.exercise.creditscore.service.ClientService;
import coding.exercise.creditscore.service.ScoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private ScoreCalculator scoreCalculator;

    public ClientServiceImpl(@Autowired ClientRepository clientRepository, @Autowired ScoreCalculator scoreCalculator) {
        this.clientRepository = clientRepository;
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Long save(Client client) {
        client = clientRepository.save(client);
        return client.getId();
    }

    @Override
    public void setClientIncome(Long id, BigDecimal income) {
        Client client = findById(id);
        Calculation calculation = client.getCalculation();

        if (calculation == null) {
            calculation = new Calculation();
        }

        calculation.setAnnualIncome(income);
        calculation.setCreditScore(scoreCalculator.calculate(client.getAge(), income));
        calculation.setClient(client);
        client.setCalculation(calculation);

        clientRepository.save(client);
    }

    @Override
    public Long getClientCreditScore(Long id) {
        Client client = findById(id);

        if (client.getCalculation() != null) {
            return client.getCalculation().getCreditScore();
        } else {
            throw new NoCreditScoreException();
        }
    }

    @Override
    public Client findById(long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            return optionalClient.get();
        } else {
            throw new ClientNotFoundException();
        }
    }
}
