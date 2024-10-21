package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.Payloads.NewUserDTO;
import angelolaera.cashuboli_capstone_backend.Payloads.NewUserRespDTO;
import angelolaera.cashuboli_capstone_backend.Payloads.UserLoginDTO;
import angelolaera.cashuboli_capstone_backend.Payloads.UserLoginRespDTO;
import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.enums.Ruolo;
import angelolaera.cashuboli_capstone_backend.exceptions.BadRequestException;
import angelolaera.cashuboli_capstone_backend.services.AuthService;
import angelolaera.cashuboli_capstone_backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    // Endpoint per il login
    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody UserLoginDTO payload) {
        // Chiama il servizio di autenticazione e restituisci il token insieme allo username
        return this.authService.checkCredentialsAndGenerateToken(payload);
    }

    // Endpoint per la registrazione
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO register(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        // Verifica errori di validazione
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            // Creazione del nuovo utente
            Utente utente = new Utente();
            utente.setNome(body.nome());
            utente.setCognome(body.cognome());
            utente.setData_di_nascita(body.data_di_nascita());
            utente.setEmail(body.email());
            utente.setPassword(body.password());
            utente.setRuolo(Ruolo.USER);  // Imposta il ruolo come USER di default

            // Salva il nuovo utente
            Utente savedUtente = this.utenteService.save(utente);

            return new NewUserRespDTO(savedUtente.getId());
        }
    }
}
