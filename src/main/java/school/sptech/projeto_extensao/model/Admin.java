package school.sptech.projeto_extensao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.websocket.Decoder;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String usuario;
    private String senha;
    private Boolean trocaSenhaObrigatoria = true;

    public Admin() {
    }

    public Admin(Integer id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.trocaSenhaObrigatoria = true;
    }

    public Admin(Integer id, String usuario, String senha, Boolean trocaSenhaObrigatoria) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.trocaSenhaObrigatoria = trocaSenhaObrigatoria;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getTrocaSenhaObrigatoria() {
        return trocaSenhaObrigatoria;
    }

    public void setTrocaSenhaObrigatoria(Boolean trocaSenhaObrigatoria) {
        this.trocaSenhaObrigatoria = trocaSenhaObrigatoria;
    }
}
