package school.sptech.projeto_extensao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Representa um administrador do sistema")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Schema(description = "ID do administrador", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nome de usuário para login", example = "admin")
    @Column(nullable = false, unique = true)
    private String usuario;

    @Schema(description = "Senha de acesso do administrador", example = "1234", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String senha;

    @Column(name = "troca_senha_obrigatoria")
    private Boolean precisaTrocarSenha = true;

    public Admin(int id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }
}
