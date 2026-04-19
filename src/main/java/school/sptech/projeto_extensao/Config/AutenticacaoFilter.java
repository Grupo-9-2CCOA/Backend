package school.sptech.projeto_extensao.Config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import school.sptech.projeto_extensao.controller.AdminController;
import school.sptech.projeto_extensao.dto.AdminDetalhesDto;
import school.sptech.projeto_extensao.service.AutenticacaoService;

import java.io.IOException;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;
    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = null;
        String jwtToken = extrairToken(request);

        if (jwtToken != null) {
            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);

            } catch (ExpiredJwtException e) {
                LOGGER.warn("[AUTENTICACAO] Token expirado para o usuário '{}': {}",
                        e.getClaims().getSubject(), e.getMessage());

            } catch (MalformedJwtException e) {
                LOGGER.warn("[AUTENTICACAO] Token com formato inválido: {}", e.getMessage());

            } catch (UnsupportedJwtException e) {
                LOGGER.warn("[AUTENTICACAO] Token com algoritmo não suportado: {}", e.getMessage());

            } catch (SecurityException e) {
                LOGGER.warn("[AUTENTICACAO] Assinatura do token inválida (possível adulteração): {}", e.getMessage());

            } catch (IllegalArgumentException e) {
                LOGGER.warn("[AUTENTICACAO] Token ausente ou vazio: {}", e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!registrarAutenticacaoNoContexto(request, response, username, jwtToken)) {
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extrairToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AdminController.COOOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private boolean registrarAutenticacaoNoContexto(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    String username,
                                                    String jwtToken) throws IOException {
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
            if (userDetails instanceof AdminDetalhesDto adminDetalhes
                    && Boolean.TRUE.equals(adminDetalhes.getTrocaSenhaObrigatoria())
                    && !isRotaPermitidaNoPrimeiroAcesso(request)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Troca de senha obrigatória");
                return false;
            }

            UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }

        return true;
    }

    private boolean isRotaPermitidaNoPrimeiroAcesso(HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (!uri.startsWith("/admin")) {
            return true;
        }

        return uri.equals("/admin/trocar-senha")
                || uri.equals("/admin/logout")
                || uri.equals("/admin/login");
    }
}
