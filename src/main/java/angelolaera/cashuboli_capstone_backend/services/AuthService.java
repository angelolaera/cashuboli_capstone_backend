package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.Payloads.UserLoginDTO;
import angelolaera.cashuboli_capstone_backend.Payloads.UserLoginRespDTO;
import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.exceptions.UnauthorizedException;
import angelolaera.cashuboli_capstone_backend.repositories.UtenteRepository;
import angelolaera.cashuboli_capstone_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public UserLoginRespDTO checkCredentialsAndGenerateToken(UserLoginDTO body) {
        // Trova l'utente tramite email
        Utente found = utenteRepository.findByEmail(body.email())
                .orElseThrow(() -> new UnauthorizedException("Credenziali errate!"));

        // Verifica se la password è corretta
        if (bcrypt.matches(body.password(), found.getPassword())) {
            // Genera il token JWT
            String token = jwtTools.createToken(found);

            // Restituisci anche l'ID e il ruolo dell'utente
            return new UserLoginRespDTO(token, found.getNome(), found.getEmail(), found.getRuolo().name(), found.getId());
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
