package school.sptech.projeto_extensao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.projeto_extensao.Config.GerenciadorTokenJwt;
import school.sptech.projeto_extensao.dto.AdminListarDto;
import school.sptech.projeto_extensao.dto.AdminMapper;
import school.sptech.projeto_extensao.dto.AdminTokenDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void criar(Admin novoAdmin) {

        String senhaCriptografada = passwordEncoder.encode(novoAdmin.getSenha());
        novoAdmin.setSenha(senhaCriptografada);
        novoAdmin.setPrecisaTrocarSenha(false);

        this.adminRepository.save(novoAdmin);
    }

    public AdminTokenDto autenticar(Admin admin) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                admin.getUsuario(), admin.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Admin adminAutenticado =
                adminRepository.findByUsuario(admin.getUsuario())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Usuário do admin não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return AdminMapper.of(adminAutenticado, token);
    }

    public void trocarSenha(String novaSenha) {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacao == null || autenticacao.getName() == null) {
            throw new ResponseStatusException(401, "Usuário não autenticado", null);
        }

        Admin admin = adminRepository.findByUsuario(autenticacao.getName())
                .orElseThrow(() -> new ResponseStatusException(404, "Usuário do admin não cadastrado", null));

        if (Boolean.FALSE.equals(admin.getPrecisaTrocarSenha())) {
            throw new ResponseStatusException(400, "A senha já foi alterada", null);
        }

        admin.setSenha(passwordEncoder.encode(novaSenha));
        admin.setPrecisaTrocarSenha(false);
        adminRepository.save(admin);
    }

    public List<AdminListarDto> listarTodos() {
        List<Admin> adminsEcontrados = adminRepository.findAll();
        return adminsEcontrados.stream().map(AdminMapper::of).toList();
    }

}
