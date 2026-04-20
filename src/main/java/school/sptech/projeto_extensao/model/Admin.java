package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.websocket.Decoder;

@Schema(description = "Representa um administrador do sistema")
@Entity
public class Admin {

    @Schema(description = "ID do administrador", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nome de usuário para login", example = "admin")
    private String usuario;

    @Schema(description = "Senha de acesso do administrador", example = "1234", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String senha;
    private Boolean precisaTrocarSenha = true;

    public Admin() {
    }

    public Admin(Integer id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.precisaTrocarSenha = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getPrecisaTrocarSenha() {
        return precisaTrocarSenha;
    }

    public void setPrecisaTrocarSenha(Boolean precisaTrocarSenha) {
        this.precisaTrocarSenha = precisaTrocarSenha;
    }
}
