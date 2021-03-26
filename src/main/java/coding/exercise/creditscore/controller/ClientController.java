package coding.exercise.creditscore.controller;

import coding.exercise.creditscore.dto.AnnualIncomeDto;
import coding.exercise.creditscore.dto.ClientDto;
import coding.exercise.creditscore.model.Client;
import coding.exercise.creditscore.service.ClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    private ModelMapper modelMapper;

    public ClientController(@Autowired ClientService clientService, @Autowired ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public Map<String, Long> createClient(@RequestBody @Valid ClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);

        Map<String, Long> result = new HashMap<>();
        result.put("id", clientService.save(client));

        return result;
    }

    @GetMapping
    public List<ClientDto> getAllClients() {
        Iterable<Client> allClients = clientService.findAll();
        Type targetListType = new TypeToken<List<ClientDto>>() {}.getType();
        return modelMapper.map(allClients, targetListType);
    }

    @PutMapping("/{id}/income")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acceptIncome(@PathVariable("id") Long id, @Valid @RequestBody AnnualIncomeDto annualIncomeDto) {
        clientService.setClientIncome(id, annualIncomeDto.getAnnualIncome());
    }

    @GetMapping("/{id}/credit-score")
    public Map<String, Long>  getCreditScore(@PathVariable("id") Long id) {
        Long creditScore = clientService.getClientCreditScore(id);
        return getCreditScoreProperty(creditScore);
    }

    public static Map<String, Long> getCreditScoreProperty(Long creditScore) {
        Map<String, Long> result = new HashMap<>();
        result.put("creditScore", creditScore);
        return result;
    }
}
