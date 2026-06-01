package school.sptech.projeto_extensao.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
