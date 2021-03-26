package coding.exercise.creditscore.service.impl;

import coding.exercise.creditscore.exception.ClientNotFoundException;
import coding.exercise.creditscore.exception.NoCreditScoreException;
import coding.exercise.creditscore.model.Calculation;
import coding.exercise.creditscore.model.Client;
import coding.exercise.creditscore.repository.ClientRepository;
import coding.exercise.creditscore.service.ClientService;
import coding.exercise.creditscore.service.ScoreCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    private ClientRepository mClientRepository = mock(ClientRepository.class);

    private ScoreCalculator mScoreCalculator = mock(ScoreCalculator.class);

    private ClientService cut = new ClientServiceImpl(mClientRepository, mScoreCalculator);

    @Test
    void shouldReturnClientFromRepository() throws Exception {
        Client expectedClient = new Client();
        when(mClientRepository.findById(anyLong())).thenReturn(Optional.of(expectedClient));
        Client actual = cut.findById(1);
        assertThat(actual, is(expectedClient));
    }

    @Test
    void shouldThrowExceptionWhenNoClient() throws Exception {
        when(mClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> cut.findById(1));
    }

    @Test
    void shouldCallFindAllFromRepository() throws Exception {
        Client expected = getClientWithId();

        List<Client> clients = Collections.singletonList(expected);
        when(mClientRepository.findAll()).thenReturn(clients);
        Iterable<Client> actual = cut.findAll();
        verify(mClientRepository).findAll();
        assertEquals(clients, actual);
    }

    @Test
    void returnClientIdAfterSave() throws Exception {
        Client client = getClientWithId();
        when(mClientRepository.save(client)).thenReturn(client);
        Long actualId = cut.save(client);
        verify(mClientRepository).save(client);
        assertThat(actualId, is(1L));
    }

    @Test
    void updateClientCalculations() throws Exception {
        Client expectedClient = getClientWithId();
        expectedClient.setDateOfBirth(LocalDate.now());

        Calculation calculation = new Calculation();
        calculation.setClient(expectedClient);
        calculation.setCreditScore(1L);
        calculation.setAnnualIncome(BigDecimal.valueOf(1L));
        expectedClient.setCalculation(calculation);

        when(mClientRepository.findById(1L)).thenReturn(Optional.of(expectedClient));
        when(mScoreCalculator.calculate(0, BigDecimal.valueOf(15))).thenReturn(50L);

        cut.setClientIncome(1L, BigDecimal.valueOf(15));

        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(mClientRepository).save(clientCaptor.capture());

        assertEquals(calculation, clientCaptor.getValue().getCalculation());
        assertThat(clientCaptor.getValue().getCalculation().getCreditScore(), is(50L));
    }

    @Test
    void shouldCreateClientCalculationAndSetIncome() throws Exception {
        Client expectedClient = getClientWithId();
        expectedClient.setDateOfBirth(LocalDate.now());

        when(mClientRepository.findById(1L)).thenReturn(Optional.of(expectedClient));
        when(mScoreCalculator.calculate(0, BigDecimal.valueOf(15))).thenReturn(50L);

        cut.setClientIncome(1L, BigDecimal.valueOf(15));

        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(mClientRepository).save(clientCaptor.capture());

        assertThat(clientCaptor.getValue().getCalculation().getCreditScore(), is(50L));
    }

    @Test
    void shouldThrowExceptionWhenNoCreditScore() throws Exception {
        when(mClientRepository.findById(1L)).thenReturn(Optional.of(getClientWithId()));
        assertThrows(NoCreditScoreException.class, () -> cut.getClientCreditScore(1L));
    }

    private Client getClientWithId() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> client = Client.class;
        Object instance = client.newInstance();
        Field idField = instance.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(instance, 1L);
        return (Client)instance;
    }

}
