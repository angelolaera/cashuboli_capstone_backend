package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.enums.StatoPrenotazione;
import angelolaera.cashuboli_capstone_backend.repositories.PrenotazioneRepository;
import angelolaera.cashuboli_capstone_backend.repositories.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private TourRepository tourRepository;

    // Recupera tutte le prenotazioni di un utente
    public List<Prenotazione> getPrenotazioniByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    // Crea una nuova prenotazione
    public Prenotazione createPrenotazione(Prenotazione prenotazione) {
        // Recupera il tour collegato alla prenotazione
        Optional<Tour> tourOptional = tourRepository.findById(prenotazione.getTour().getId());
        if (tourOptional.isEmpty()) {
            throw new IllegalArgumentException("Tour non trovato");
        }

        Tour tour = tourOptional.get();

        // Calcola il totale prezzo (numero di biciclette prenotate * prezzo del tour)
        BigDecimal totalePrezzo = tour.getPrice().multiply(BigDecimal.valueOf(prenotazione.getNumeroBiciclettePrenotate()));
        prenotazione.setTotalePrezzo(totalePrezzo);

        // Imposta la data della prenotazione e lo stato come "CONFERMATA"
        prenotazione.setDataPrenotazione(LocalDate.now());
        prenotazione.setStato(StatoPrenotazione.CONFERMATA);

        return prenotazioneRepository.save(prenotazione);
    }

    // Cancella una prenotazione (modifica lo stato a "CANCELLATA")
    public void cancellaPrenotazione(Long prenotazioneId) {
        Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(prenotazioneId);
        if (prenotazioneOptional.isPresent()) {
            Prenotazione prenotazione = prenotazioneOptional.get();
            prenotazione.setStato(StatoPrenotazione.CANCELLATA);
            prenotazioneRepository.save(prenotazione);
        } else {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
    }
}
