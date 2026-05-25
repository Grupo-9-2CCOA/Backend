package school.sptech.projeto_extensao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminTokenDto {

    private Integer id;
    private String usuario;
    private String token;
    private Boolean trocaSenhaObrigatoria;
}
