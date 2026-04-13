package school.sptech.projeto_extensao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import school.sptech.projeto_extensao.dto.AdminCriacaoDto;
import school.sptech.projeto_extensao.dto.AdminListarDto;
import school.sptech.projeto_extensao.dto.AdminLoginDto;
import school.sptech.projeto_extensao.dto.AdminMapper;
import school.sptech.projeto_extensao.dto.AdminSessaoDto;
import school.sptech.projeto_extensao.dto.AdminTokenDto;
import school.sptech.projeto_extensao.dto.AdminTrocarSenhaDto;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.service.AdminService;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Endpoints para autenticação e gerenciamento do administrador")
public class AdminController {

    public static final String COOOKIE_NAME = "authToken";

    @Value("${jwt.validity}")
    private Long jwtValidity;

    @Autowired
    private AdminService adminService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Criar administrador",
            description = "Cria um novo administrador no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    public ResponseEntity<Void> criar(@RequestBody @Valid AdminCriacaoDto adminCriacaoDto) {
        Admin novoAdmin = AdminMapper.of(adminCriacaoDto);
        this.adminService.criar(novoAdmin);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login do administrador",
            description = "Autentica o administrador e retorna os dados da sessão"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AdminSessaoDto.class))),
            @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
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
    @Operation(
            summary = "Logout do administrador",
            description = "Remove o cookie de autenticação da sessão atual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout realizado com sucesso")
    })
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

    @PostMapping("/trocar-senha")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Trocar senha do administrador",
            description = "Altera a senha do administrador autenticado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "A senha já foi alterada", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
    public ResponseEntity<Void> trocarSenha(@RequestBody @Valid AdminTrocarSenhaDto adminTrocarSenhaDto) {
        this.adminService.trocarSenha(adminTrocarSenhaDto.getSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar administradores",
            description = "Retorna a lista de administradores cadastrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AdminListarDto.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum administrador encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    public ResponseEntity<List<AdminListarDto>> listarTodos() {
        List<AdminListarDto> adminsEncontrados = this.adminService.listarTodos();

        if (adminsEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(adminsEncontrados);
    }

}
