package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AdminLoginDto {

    @Schema(description = "Usuário do admin", example = "admin123")
    private String usuario;
    @Schema(description = "Senha do admin", example = "123456")
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
