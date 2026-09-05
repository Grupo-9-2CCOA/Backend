package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.projeto_extensao.dto.admin.AdminCriacaoDto;
import school.sptech.projeto_extensao.dto.admin.AdminListarDto;
import school.sptech.projeto_extensao.dto.admin.AdminLoginDto;
import school.sptech.projeto_extensao.mapper.AdminMapper;
import school.sptech.projeto_extensao.dto.admin.AdminSessaoDto;
import school.sptech.projeto_extensao.dto.admin.AdminTokenDto;
import school.sptech.projeto_extensao.dto.admin.AdminTrocarSenhaDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.service.AdminService;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final String COOOKIE_NAME = "authToken";

    @Value("${jwt.validity}")
    private Long jwtValidity;

    @Autowired
    private AdminService adminService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid AdminCriacaoDto adminCriacaoDto) {
        Admin novoAdmin = AdminMapper.of(adminCriacaoDto);
        this.adminService.criar(novoAdmin);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AdminSessaoDto> login(@RequestBody AdminLoginDto adminLoginDto,
                                                HttpServletResponse response) {

        Admin admin = AdminMapper.of(adminLoginDto);
        AdminTokenDto autenticado = this.adminService.autenticar(admin);

        ResponseCookie cookie = ResponseCookie.from(COOOKIE_NAME, autenticado.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(jwtValidity))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AdminSessaoDto sessao = AdminMapper.ofSessao(autenticado);
        return ResponseEntity.ok(sessao);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessao")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> verificarSessao() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/trocar-senha")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> trocarSenha(@RequestBody @Valid AdminTrocarSenhaDto dto) {
        this.adminService.trocarSenha(dto.getSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<AdminListarDto>> listarTodos() {
        List<AdminListarDto> adminsEncontrados = this.adminService.listarTodos();

        if (adminsEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(adminsEncontrados);
    }

}
