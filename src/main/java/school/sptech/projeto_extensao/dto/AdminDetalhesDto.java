package school.sptech.projeto_extensao.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.projeto_extensao.model.Admin;

import java.util.Collection;

public class AdminDetalhesDto implements UserDetails {

    private final String usuario;

    private final String senha;

    public AdminDetalhesDto(Admin admin) {
        this.usuario = admin.getUsuario();
        this.senha = admin.getSenha();
    }

    public String getUsuario() {return usuario;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
