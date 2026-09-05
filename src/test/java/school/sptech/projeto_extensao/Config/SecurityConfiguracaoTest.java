package school.sptech.projeto_extensao.Config;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.projeto_extensao.controller.AdminController;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfiguracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void prepararAdmin() {
        adminRepository.deleteAll();
        adminRepository.save(new Admin(null, "admin-seguranca", passwordEncoder.encode("senha123"), false));
    }

    @Test
    void deveBloquearEndpointPrivadoSemJwt() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void devePermitirLoginSemJwt() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"usuario":"admin-seguranca","senha":"senha123"}
                                """))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(AdminController.COOOKIE_NAME));
    }

    @Test
    void devePermitirEndpointPrivadoComJwtNoCookie() throws Exception {
        Cookie cookieJwt = mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"usuario":"admin-seguranca","senha":"senha123"}
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getCookie(AdminController.COOOKIE_NAME);

        mockMvc.perform(get("/clientes").cookie(cookieJwt))
                .andExpect(status().isNoContent());
    }
}
