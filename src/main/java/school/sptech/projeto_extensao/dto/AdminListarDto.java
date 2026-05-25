package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminListarDto {

    @Schema(description = "ID do administrador", example = "1")
    private Integer id;

    @Schema(description = "Nome do administrador", example = "Admin")
    private String usuario;
}
