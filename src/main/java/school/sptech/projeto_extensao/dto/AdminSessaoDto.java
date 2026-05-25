package school.sptech.projeto_extensao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSessaoDto {

    private Integer id;
    private String usuario;
    private Boolean trocaSenhaObrigatoria;
}
