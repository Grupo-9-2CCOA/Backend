package school.sptech.projeto_extensao.controller;

import jakarta.validation.Valid;
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

    //Listar Clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listar(){
        List<Cliente> clientes = clienteService.listar();
        if(clientes.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        List<ClienteResponseDto> responseDto = ClienteMapper.toDto(clientes);
        return ResponseEntity.status(200).body(responseDto);
    }

    //Listar Cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(
            @PathVariable Integer id
    ){
        Cliente cliente = clienteService.findById(id);
        ClienteResponseDto responseDto = ClienteMapper.toDto(cliente);
        return ResponseEntity.status(200).body(responseDto);
    }

    //Criar Cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(
            @Valid @RequestBody ClienteRequestDto dto
            ){
        Cliente entidade = ClienteMapper.toEntity(dto);
        Cliente clienteCadastrado = clienteService.cadastrar(entidade);

        ClienteResponseDto resposta = ClienteMapper.toDto(clienteCadastrado);
        return ResponseEntity.status(201).body(resposta);
    }

    //"Deletar" Cliente
    @PutMapping
    public ResponseEntity<Void> inativarCliente(
            @PathVariable Integer id
    ){
        Boolean resposta = clienteService.deletar(id);

        if(resposta == false){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).build();
    }

    //Atualizar Cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizar(
            @RequestBody @Valid ClienteRequestDto cliente,
            @PathVariable Integer id
    ){
        ClienteResponseDto resposta = ClienteMapper.toDto(clienteService.atualizar(id, ClienteMapper.toEntity(cliente)));
        return ResponseEntity.status(200).body(resposta);
    }
}
