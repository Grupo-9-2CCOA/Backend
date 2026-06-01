package school.sptech.projeto_extensao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.dto.admin.AdminDetalhesDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOpt = adminRepository.findFirstByUsuarioOrderByIdDesc(username);

        if (adminOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Admin: %s não encontrado", username));
        }

        return new AdminDetalhesDto(adminOpt.get());
    }
}
