package school.sptech.projeto_extensao.dto;

public class AdminTokenDto {

    private Integer id;
    private String usuario;
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getTrocaSenhaObrigatoria() {
        return trocaSenhaObrigatoria;
    }

    public void setTrocaSenhaObrigatoria(Boolean trocaSenhaObrigatoria) {
        this.trocaSenhaObrigatoria = trocaSenhaObrigatoria;
    }
}
