package school.sptech.projeto_extensao.dto;

public class AdminTokenDto {

    private Integer id;
    private String usuario;
    private String token;
    private Boolean precisaTrocarSenha;

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

    public Boolean getPrecisaTrocarSenha() {
        return precisaTrocarSenha;
    }

    public void setPrecisaTrocarSenha(Boolean precisaTrocarSenha) {
        this.precisaTrocarSenha = precisaTrocarSenha;
    }
}
