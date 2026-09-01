package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDto {

    @NotBlank(message = "O logradouro não pode estar em branco.")
    @Schema(example = "Avenida Paulista", description = "Logradouro do endereço")
    private String logradouro;

    @NotBlank(message = "O número do endereço não pode estar em branco.")
    @Pattern(regexp = "\\d+", message = "O número do endereço deve conter apenas números.")
    @Schema(example = "75", description = "Número do Endereço")
    private String numero;

    @NotBlank(message = "O CEP não pode estar em branco.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 números.")
    @Schema(example = "01311000", description = "CEP do endereço")
    private String cep;

    @Schema(example = "APTO 969", description = "Complemento do endereço (Opcional)")
    private String complemento;

    @Schema(example = "1", description = "ID do Cliente vinculado ao endereço")
    private Long idCliente;
}
