package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.mapper.ClienteMapper;
import school.sptech.projeto_extensao.dto.cliente.ClienteRequestDto;
import school.sptech.projeto_extensao.dto.cliente.ClienteResponseDto;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.service.ClienteService;

import java.util.List;

@Tag(name = "Clientes", description = "Gestão de clientes do sistema")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna uma lista de clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listar(){
        List<Cliente> clientes = clienteService.listar();
        if(clientes.isEmpty()){
            return ResponseEntity.status(204).build(); // Alterado para 204 (No Content) por convenção
        }
        List<ClienteResponseDto> responseDto = ClienteMapper.toDto(clientes);
        return ResponseEntity.status(200).body(responseDto);
    }

    @Operation(summary = "Busca um cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "ID não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Integer id){
        Cliente cliente = clienteService.findById(id);
        ClienteResponseDto responseDto = ClienteMapper.toDto(cliente);
        return ResponseEntity.status(200).body(responseDto);
    }

    @Operation(summary = "Cadastra um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(@Valid @RequestBody ClienteRequestDto dto){
        Cliente entidade = ClienteMapper.toEntity(dto);
        Cliente clienteCadastrado = clienteService.cadastrar(entidade);
        ClienteResponseDto resposta = ClienteMapper.toDto(clienteCadastrado);
        return ResponseEntity.status(201).body(resposta);
    }

    @Operation(summary = "Inativa um cliente (Exclusão lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente inativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a inativação")
    })
    @PatchMapping("/{id}") // Sugestão: Alterado para PATCH ou DELETE por ser uma alteração parcial/status
    public ResponseEntity<Void> inativarCliente(@PathVariable Integer id){
        Boolean resposta = clienteService.deletar(id);
        if(!resposta){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Atualiza os dados de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizar(
            @RequestBody @Valid ClienteRequestDto cliente,
            @PathVariable Integer id
    ){
        ClienteResponseDto resposta = ClienteMapper.toDto(clienteService.atualizar(id, ClienteMapper.toEntity(cliente)));
        return ResponseEntity.status(200).body(resposta);
    }
}
