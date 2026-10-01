package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;
import school.sptech.projeto_extensao.dto.EnderecoResponseDto;
import school.sptech.projeto_extensao.service.EnderecoService;

@Tag(name = "Endereços", description = "Gestão de endereços do sistema")
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @Operation(summary = "Lista todos os endereços relacionados a um Cliente", description = "Retorna uma lista de endereços cadastrados e relacionados a um Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado relacionado ao cliente")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Page<EnderecoResponseDto>> listarPorCliente(@PathVariable Integer id,
                                                                      @ParameterObject
                                                                      @PageableDefault(
                                                                              size = 10,
                                                                              page = 0,
                                                                              direction = Sort.Direction.ASC,
                                                                              sort = "logradouro"
                                                                      )Pageable pageable) {
        Page<EnderecoResponseDto> resposta = enderecoService.listarPorCliente(id, pageable);

        if (resposta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Cadastra um novo endereço vinculado a um Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    @PostMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> cadastrar(
            @PathVariable Integer id,
            @RequestBody @Valid EnderecoRequestDto dto
    ) {
        EnderecoResponseDto resposta = enderecoService.cadastrar(id, dto);
        return ResponseEntity.status(201).body(resposta);
    }

    @Operation(summary = "Atualiza os dados de um endereço de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid EnderecoRequestDto dto
    ) {
        EnderecoResponseDto resposta = enderecoService.atualizar(id, dto);
        return ResponseEntity.ok(resposta);
    }

    @Operation(summary = "Realiza uma deleção lógica de um endereço de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao processar a deleção"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
