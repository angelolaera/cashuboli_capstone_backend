package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.repositories.UtenteRepository;
import angelolaera.cashuboli_capstone_backend.repositories.PrenotazioneRepository;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Metodo per salvare un nuovo utente
    public Utente save(Utente utente) {
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
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

