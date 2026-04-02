package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class ClienteRequestDto {
    @NotBlank
    @Schema(example = "Mônica Alencar", description = "Nome do cliente")
    private String nome;

    @NotBlank
    @Schema(example = "111-222-333-44", description = "CPF do cliente")
    private String cpf;

    @NotBlank
    @Schema(example = "91234-5678")
    private String telefone;

    public ClienteRequestDto() {
    }

    public ClienteRequestDto(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
