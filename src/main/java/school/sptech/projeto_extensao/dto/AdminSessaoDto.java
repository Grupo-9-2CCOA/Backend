package school.sptech.projeto_extensao.dto;

public class AdminSessaoDto {

    private Integer id;
    private String usuario;
    private Boolean trocaSenhaObrigatoria;

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

    public Boolean getTrocaSenhaObrigatoria() {
        return trocaSenhaObrigatoria;
    }

    public void setTrocaSenhaObrigatoria(Boolean trocaSenhaObrigatoria) {
        this.trocaSenhaObrigatoria = trocaSenhaObrigatoria;
    }
}
