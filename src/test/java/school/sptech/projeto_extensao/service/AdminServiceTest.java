package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.projeto_extensao.Config.GerenciadorTokenJwt;
import school.sptech.projeto_extensao.dto.admin.AdminListarDto;
import school.sptech.projeto_extensao.dto.admin.AdminTokenDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AdminService adminService;

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Testar função criar se salva admin com senha criptografada")
    void testarFuncaoCriarSeSalvaAdminComSenhaCriptografada() {
        Admin admin = new Admin();
        admin.setUsuario("admin123");
        admin.setSenha("123456");

        when(adminRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode("123456")).thenReturn("senha-criptografada");
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adminService.criar(admin);

        ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(adminCaptor.capture());

        Admin adminSalvo = adminCaptor.getValue();
        Assertions.assertEquals("admin123", adminSalvo.getUsuario());
        Assertions.assertEquals("senha-criptografada", adminSalvo.getSenha());
        Assertions.assertTrue(adminSalvo.getPrecisaTrocarSenha());
        verify(passwordEncoder).encode("123456");
    }

    @Test
    @DisplayName("Testar função criar se lança conflito quando já existe admin")
    void testarFuncaoCriarSeLancaConflitoQuandoJaExisteAdmin() {
        Admin admin = new Admin();
        admin.setUsuario("admin123");
        admin.setSenha("123456");

        when(adminRepository.count()).thenReturn(1L);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adminService.criar(admin)
        );

        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    @DisplayName("Testar função autenticar se retorna token do admin")
    void testarFuncaoAutenticarSeRetornaTokenDoAdmin() {
        Admin adminLogin = new Admin();
        adminLogin.setUsuario("admin123");
        adminLogin.setSenha("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken("admin123", null);

        Admin adminEncontrado = new Admin(1, "admin123", "senha-criptografada");
        adminEncontrado.setPrecisaTrocarSenha(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.of(adminEncontrado));
        when(gerenciadorTokenJwt.generateToken(authentication))
                .thenReturn("token-abc-123");

        AdminTokenDto retorno = adminService.autenticar(adminLogin);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.getId());
        Assertions.assertEquals("admin123", retorno.getUsuario());
        Assertions.assertEquals("token-abc-123", retorno.getToken());
        Assertions.assertTrue(retorno.getTrocaSenhaObrigatoria());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(gerenciadorTokenJwt).generateToken(authentication);
    }

    @Test
    @DisplayName("Testar função autenticar se lança 404 quando admin não é encontrado")
    void testarFuncaoAutenticarSeLanca404QuandoAdminNaoEncontrado() {
        Admin adminLogin = new Admin();
        adminLogin.setUsuario("admin123");
        adminLogin.setSenha("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken("admin123", null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adminService.autenticar(adminLogin)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Usuário do admin não cadastrado", exception.getReason());
        verify(gerenciadorTokenJwt, never()).generateToken(any(Authentication.class));
    }

    @Test
    @DisplayName("Testar função trocarSenha se atualiza senha e desativa obrigatoriedade")
    void testarFuncaoTrocarSenhaSeAtualizaSenhaEDesativaObrigatoriedade() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin123", null)
        );

        Admin admin = new Admin(1, "admin123", "senha-antiga");
        admin.setPrecisaTrocarSenha(true);

        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.of(admin));
        when(passwordEncoder.encode("654321"))
                .thenReturn("senha-nova-criptografada");
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adminService.trocarSenha("654321");

        ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(adminCaptor.capture());

        Admin adminSalvo = adminCaptor.getValue();
        Assertions.assertEquals("senha-nova-criptografada", adminSalvo.getSenha());
        Assertions.assertFalse(adminSalvo.getPrecisaTrocarSenha());
        verify(passwordEncoder).encode("654321");
    }

    @Test
    @DisplayName("Testar função trocarSenha se lança 401 sem autenticação")
    void testarFuncaoTrocarSenhaSeLanca401SemAutenticacao() {
        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adminService.trocarSenha("654321")
        );

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        Assertions.assertEquals("Usuário não autenticado", exception.getReason());
    }

    @Test
    @DisplayName("Testar função trocarSenha se lança 404 quando admin não existe")
    void testarFuncaoTrocarSenhaSeLanca404QuandoAdminNaoExiste() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin123", null)
        );

        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adminService.trocarSenha("654321")
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Usuário do admin não cadastrado", exception.getReason());
        verify(passwordEncoder, never()).encode(anyString());
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    @DisplayName("Testar função trocarSenha se lança 400 quando a senha já foi alterada")
    void testarFuncaoTrocarSenhaSeLanca400QuandoSenhaJaFoiAlterada() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin123", null)
        );

        Admin admin = new Admin(1, "admin123", "senha-antiga");
        admin.setPrecisaTrocarSenha(false);

        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.of(admin));

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adminService.trocarSenha("654321")
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals("A senha já foi alterada", exception.getReason());
        verify(passwordEncoder, never()).encode(anyString());
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    @DisplayName("Testar função listarTodos se retorna lista vazia")
    void testarFuncaoListarTodosSeRetornaListaVazia() {
        when(adminRepository.findAll()).thenReturn(new ArrayList<>());

        List<AdminListarDto> retorno = adminService.listarTodos();

        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("Testar função listarTodos se retorna admins mapeados")
    void testarFuncaoListarTodosSeRetornaAdminsMapeados() {
        List<Admin> admins = new ArrayList<>();
        Admin admin1 = new Admin(1, "admin123", "senha1");
        Admin admin2 = new Admin(2, "admin456", "senha2");
        admins.add(admin1);
        admins.add(admin2);

        when(adminRepository.findAll()).thenReturn(admins);

        List<AdminListarDto> retorno = adminService.listarTodos();

        Assertions.assertEquals(2, retorno.size());
        Assertions.assertEquals(1, retorno.get(0).getId());
        Assertions.assertEquals("admin123", retorno.get(0).getUsuario());
        Assertions.assertEquals(2, retorno.get(1).getId());
        Assertions.assertEquals("admin456", retorno.get(1).getUsuario());
    }
}

