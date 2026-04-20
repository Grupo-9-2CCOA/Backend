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
import school.sptech.projeto_extensao.dto.AdminTrocarSenhaDto;
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
        novoAdmin.setTrocaSenhaObrigatoria(true);

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

    public List<AdminListarDto> listarTodos() {
        List<Admin> adminsEcontrados = adminRepository.findAll();
        return adminsEcontrados.stream().map(AdminMapper::of).toList();
    }

    public void trocarSenha(AdminTrocarSenhaDto dto) {
        String usuarioAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

        Admin admin = adminRepository.findByUsuario(usuarioAutenticado)
                .orElseThrow(() -> new ResponseStatusException(404, "Admin não encontrado", null));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), admin.getSenha())) {
            throw new ResponseStatusException(401, "Senha atual inválida", null);
        }

        if (dto.getSenhaAtual().equals(dto.getNovaSenha())) {
            throw new ResponseStatusException(400, "A nova senha deve ser diferente da senha atual", null);
        }

        admin.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        admin.setTrocaSenhaObrigatoria(false);
        adminRepository.save(admin);
    }

}
