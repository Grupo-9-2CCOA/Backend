package school.sptech.projeto_extensao.dto;

public class AdminSessaoDto {

    private Integer id;
    private String usuario;
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

    public Boolean getPrecisaTrocarSenha() {
        return precisaTrocarSenha;
    }

    public void setPrecisaTrocarSenha(Boolean precisaTrocarSenha) {
        this.precisaTrocarSenha = precisaTrocarSenha;
    }
}
