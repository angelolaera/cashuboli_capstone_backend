package angelolaera.cashuboli_capstone_backend.services;
import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.repositories.UtenteRepository;
import angelolaera.cashuboli_capstone_backend.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PrenotazioneRepository prenotazioneRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.prenotazioneRepository = prenotazioneRepository;
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

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Transactional
    public Utente save(Utente utente) {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new IllegalArgumentException("Email già registrata!");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente.setEnabled(false); // L'utente è disabilitato fino alla verifica
        Utente savedUser = utenteRepository.save(utente);



        // Invia email di verifica con il link
        String welcomeEmailLink = frontendBaseUrl;
        emailService.sendWelcomeEmail(utente.getEmail(), utente.getNome(), welcomeEmailLink);


        return savedUser;
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
