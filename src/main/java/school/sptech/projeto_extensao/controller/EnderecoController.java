package school.sptech.projeto_extensao.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;
import school.sptech.projeto_extensao.dto.EnderecoResponseDto;
import school.sptech.projeto_extensao.service.EnderecoService;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EnderecoResponseDto>> listarPorCliente(@PathVariable Integer id) {
        List<EnderecoResponseDto> resposta = enderecoService.listarPorCliente(id);

        if (resposta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> cadastrar(
            @PathVariable Integer id,
            @RequestBody @Valid EnderecoRequestDto dto
    ) {
        EnderecoResponseDto resposta = enderecoService.cadastrar(id, dto);
        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid EnderecoRequestDto dto
    ) {
        EnderecoResponseDto resposta = enderecoService.atualizar(id, dto);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
