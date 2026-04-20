package school.sptech.projeto_extensao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminTrocarSenhaDto {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 20)
    private String novaSenha;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}

