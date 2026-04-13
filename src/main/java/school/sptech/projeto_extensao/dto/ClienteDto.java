package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados resumidos de um cliente")
public class ClienteDto {
    @Schema(description = "ID do cliente", example = "1")
    private Integer id;

    @Schema(description = "Nome do cliente", example = "Ana Silva")
    private String nome;

    @Schema(description = "Telefone do cliente", example = "11999990001")
    private String telefone;

    @Schema(description = "CPF do cliente", example = "11111111111")
    private String cpf;

    public ClienteDto(Integer id, String nome, String telefone, String cpf) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
