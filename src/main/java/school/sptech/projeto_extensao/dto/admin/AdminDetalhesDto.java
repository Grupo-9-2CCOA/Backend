package school.sptech.projeto_extensao.dto.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.projeto_extensao.model.Admin;

import java.util.Collection;
import java.util.List;

public class AdminDetalhesDto implements UserDetails {

    private final String usuario;

    private final String senha;
    private final Boolean precisaTrocarSenha;

    public AdminDetalhesDto(Admin admin) {
        this.usuario = admin.getUsuario();
        this.senha = admin.getSenha();
        this.precisaTrocarSenha = admin.getPrecisaTrocarSenha();
    }

    public String getUsuario() {return usuario;}

    public Boolean getPrecisaTrocarSenha() {
        return precisaTrocarSenha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
