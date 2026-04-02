package school.sptech.projeto_extensao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.projeto_extensao.dto.ClienteMapper;
import school.sptech.projeto_extensao.dto.ClienteResponseDto;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listar(){
        List<Cliente> clientes = clienteService.listar();
        if(clientes.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        List<ClienteResponseDto> responseDto = ClienteMapper.to
    }
}
