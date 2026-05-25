package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDto {

    @NotBlank
    @Schema(example = "Avenida Paulista", description = "Logradouro do endereço")
    private String logradouro;

    @NotBlank
    @Schema(example = "75", description = "Número do Endereço")
    private String numero;

    @NotBlank
    @Size(min = 8, max = 8)
    @Schema(example = "12345-959", description = "CEP do endereço")
    private String cep;

    @Schema(example = "APTO 969", description = "Complemento do endereço (Opcional)")
    private String complemento;
    
    @NotNull
    @Schema(example = "1", description = "ID do Cliente vinculado ao endereço")
    private Long idCliente;
}
