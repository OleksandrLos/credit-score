package coding.exercise.creditscore.controller;

import coding.exercise.creditscore.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ClientService clientService;

    @Test
    void whenPostClintWithValidBodyReturn200() throws Exception {
        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jhon\",\"lastName\": \"Smith\",\"dateOfBirth\": \"23.02.2000\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostClientWithInvalidBodyThenReturn403() throws Exception {
        String body = "{\"firstName\": \"Jhon\",\"dateOfBirth\": \"23.02.2000\"}";

        mockMvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetAllClientReturn200() throws Exception {
        mockMvc.perform(get("/clients")).andExpect(status().isOk());
    }

    @Test
    void whenPutIncomeWithValidBodyThenReturn202() throws Exception {
        mockMvc.perform(put("/clients/{id}/income", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"annualIncome\": 15}"))
                .andExpect(status().isAccepted());
    }

    @Test
    void whenPutIncomeWithInvalidBodyThenReturn400() throws Exception {
        mockMvc.perform(put("/clients/{id}/income", 1)).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetCreditScoreThenReturn200() throws Exception {
        mockMvc.perform(get("/clients/{id}/credit-score", 1)).andExpect(status().isOk());
    }
}