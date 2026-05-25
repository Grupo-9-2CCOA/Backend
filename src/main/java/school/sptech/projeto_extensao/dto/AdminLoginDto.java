package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginDto {

    @Schema(description = "Usuário do admin", example = "admin123")
    private String usuario;
    @Schema(description = "Senha do admin", example = "123456")
    private String senha;
}
