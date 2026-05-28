package school.sptech.projeto_extensao.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.projeto_extensao.dto.EnderecoRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDto {
    @NotBlank
    @Schema(example = "Mônica Alencar", description = "Nome do cliente")
    private String nome;

    @NotBlank
    @Schema(example = "111-222-333-44", description = "CPF do cliente")
    private String cpf;

    @NotBlank
    @Schema(example = "91234-5678")
    private String telefone;

    private EnderecoRequestDto endereco;
}
