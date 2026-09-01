package school.sptech.projeto_extensao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoResponseDto {
    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;

    public EnderecoResponseDto(String logradouro, String numero, String complemento, String cep) {
        this(null, logradouro, numero, complemento, cep);
    }
}
