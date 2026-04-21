package school.sptech.projeto_extensao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AdminListarDto {

    @Schema(description = "ID do administrador", example = "1")
    private Integer id;

    @Schema(description = "Nome do administrador", example = "Admin")
    private String usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
