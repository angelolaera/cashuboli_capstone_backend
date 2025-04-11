package angelolaera.cashuboli_capstone_backend.security;

import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.exceptions.UnauthorizedException;
import angelolaera.cashuboli_capstone_backend.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtenteService utenteService;

    @Autowired
    public JWTCheckFilter(JWTTools jwtTools, UtenteService utenteService) {
        this.jwtTools = jwtTools;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Se manca il token o non è valido
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore inserisci correttamente il token nell'Authorization Header");
        }

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        String id = jwtTools.extractIdFromToken(accessToken);
        Long userId = Long.parseLong(id);
        Utente currentUser = utenteService.findById(userId).orElseThrow(() -> new UnauthorizedException("Utente non trovato!"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        System.out.println("🌐 PATH RICHIESTO: " + path);

        // Salta il filtro per tutte le richieste a /auth
        if (path.startsWith("/auth")) {
            System.out.println("⛔ SKIP JWT per " + path);
            return true;
        }

        // Accesso pubblico per alcune GET
        if ((path.equals("/api/tours") || path.equals("/api/biciclette")) && method.equals("GET")) {
            return true;
        }

        return false;
    }
}
