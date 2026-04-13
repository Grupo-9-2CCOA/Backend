package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public class AdminCriacaoDto {

    @Size(min = 3, max = 10)
    @Schema(description = "Nome do usuário", example = "admin")
    private String usuario;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
