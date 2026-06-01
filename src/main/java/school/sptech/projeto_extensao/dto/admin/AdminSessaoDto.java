package school.sptech.projeto_extensao.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSessaoDto {

    private Integer id;
    private String usuario;
    private Boolean trocaSenhaObrigatoria;
}
