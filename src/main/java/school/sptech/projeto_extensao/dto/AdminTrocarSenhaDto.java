package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public class AdminTrocarSenhaDto {

    @Size(min = 6, max = 20)
    @Schema(description = "Nova senha do admin", example = "654321")
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
