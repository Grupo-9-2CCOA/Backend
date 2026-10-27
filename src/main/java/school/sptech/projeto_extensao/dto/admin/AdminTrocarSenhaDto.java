package school.sptech.projeto_extensao.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminTrocarSenhaDto {

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "Nova senha do admin", example = "654321")
    private String senha;

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "Confirmação da nova senha do admin", example = "654321")
    private String confirmarSenha;

    @AssertTrue(message = "As senhas devem ser iguais.")
    public boolean isSenhasIguais() {
        if (senha == null || confirmarSenha == null) {
            return false;
        }
        return senha.equals(confirmarSenha);
    }
}
