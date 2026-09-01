package school.sptech.projeto_extensao.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteResponseDto {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private Boolean ativo;
}
