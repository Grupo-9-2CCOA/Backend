package school.sptech.projeto_extensao.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCriacaoDto {

    @Size(min = 3, max = 10)
    @Schema(description = "Nome do usuário", example = "admin")
    private String usuario;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
}
