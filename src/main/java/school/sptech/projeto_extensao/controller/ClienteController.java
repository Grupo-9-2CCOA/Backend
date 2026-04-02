package school.sptech.projeto_extensao.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.ClienteMapper;
import school.sptech.projeto_extensao.dto.ClienteRequestDto;
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
        List<ClienteResponseDto> responseDto = ClienteMapper.toDto(clientes);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(
            @PathVariable Integer id
    ){
        Cliente cliente = clienteService.findById(id);
        ClienteResponseDto responseDto = ClienteMapper.toDto(cliente);
        return ResponseEntity.status(200).build();
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(
            @Valid @RequestBody ClienteRequestDto dto
            ){
        Cliente entidade = ClienteMapper.toEntity(dto);
        Cliente clienteCadastrado = clienteService.cadastrar(entidade);

        ClienteResponseDto resposta = ClienteMapper.toDto(clienteCadastrado);
        return ResponseEntity.status(201).body(resposta);
    }
}
