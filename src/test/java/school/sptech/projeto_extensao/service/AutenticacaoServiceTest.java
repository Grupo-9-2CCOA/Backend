package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.projeto_extensao.dto.admin.AdminDetalhesDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Test
    @DisplayName("Testar função loadUserByUsername se retorna detalhes do admin")
    void testarFuncaoLoadUserByUsernameSeRetornaDetalhesDoAdmin() {
        Admin admin = new Admin(1, "admin123", "senha-criptografada");
        admin.setPrecisaTrocarSenha(true);

        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.of(admin));

        UserDetails retorno = autenticacaoService.loadUserByUsername("admin123");

        Assertions.assertNotNull(retorno);
        Assertions.assertInstanceOf(AdminDetalhesDto.class, retorno);
        Assertions.assertEquals("admin123", retorno.getUsername());
        Assertions.assertEquals("senha-criptografada", retorno.getPassword());
        Assertions.assertTrue(((AdminDetalhesDto) retorno).getPrecisaTrocarSenha());
        verify(adminRepository).findFirstByUsuarioOrderByIdDesc("admin123");
    }

    @Test
    @DisplayName("Testar função loadUserByUsername se lança UsernameNotFoundException quando admin não existe")
    void testarFuncaoLoadUserByUsernameSeLancaUsernameNotFoundExceptionQuandoAdminNaoExiste() {
        when(adminRepository.findFirstByUsuarioOrderByIdDesc("admin123"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exception = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> autenticacaoService.loadUserByUsername("admin123")
        );

        Assertions.assertEquals("Admin: admin123 não encontrado", exception.getMessage());
        verify(adminRepository).findFirstByUsuarioOrderByIdDesc("admin123");
    }
}

