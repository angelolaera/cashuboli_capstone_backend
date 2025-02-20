package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.entities.VerificationToken;
import angelolaera.cashuboli_capstone_backend.repositories.UtenteRepository;
import angelolaera.cashuboli_capstone_backend.repositories.PrenotazioneRepository;
import angelolaera.cashuboli_capstone_backend.repositories.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PrenotazioneRepository prenotazioneRepository,
                         VerificationTokenRepository tokenRepository, EmailService emailService,
                         PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.prenotazioneRepository = prenotazioneRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    public List<Prenotazione> getPrenotazioni(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    public List<Prenotazione> getPrenotazioniCancellabili(Long utenteId) {
        LocalDate dataLimite = LocalDate.now().plusDays(4);
        return prenotazioneRepository.findByUtenteIdAndDataPrenotazioneAfter(utenteId, dataLimite);
    }

    @Transactional
    public Utente save(Utente utente) {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new IllegalArgumentException("Email già registrata!");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente.setEnabled(false); // L'utente è disabilitato fino alla verifica
        Utente savedUser = utenteRepository.save(utente);

        // Genera il token di verifica
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedUser, LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);

        // Invia email di verifica con il link
        String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(utente.getEmail(), utente.getNome(), verificationLink);

        return savedUser;
    }

    @Transactional
    public boolean verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token non valido o scaduto"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token scaduto! Richiedi una nuova verifica.");
        }

        Utente user = verificationToken.getUtente();
        user.setEnabled(true);
        utenteRepository.save(user);
        tokenRepository.delete(verificationToken);
        return true;
    }

    public Utente updateProfilo(Utente utente, String nuovaPassword) {
        if (nuovaPassword != null && !nuovaPassword.isEmpty()) {
            utente.setPassword(passwordEncoder.encode(nuovaPassword));
        }
        return utenteRepository.save(utente);
    }

    public void deleteUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    public void deletePrenotazione(Long prenotazioneId) {
        prenotazioneRepository.deleteById(prenotazioneId);
    }
}
