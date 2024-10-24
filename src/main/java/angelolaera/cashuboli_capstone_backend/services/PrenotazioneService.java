package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.Payloads.PrenotazioneDTO;
import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.enums.StatoPrenotazione;
import angelolaera.cashuboli_capstone_backend.repositories.BiciclettaRepository;
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

    @Autowired
    private BiciclettaRepository biciclettaRepository;

    //Torna tutte le prenotazioni
    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }

    // Crea una nuova prenotazione
    public Prenotazione createPrenotazione(PrenotazioneDTO prenotazioneDTO, Utente utente) {
        // Recupera il tour collegato alla prenotazione
        Optional<Tour> tourOptional = tourRepository.findById(prenotazioneDTO.tourId());
        if (tourOptional.isEmpty()) {
            throw new IllegalArgumentException("Tour non trovato");
        }
        Tour tour = tourOptional.get();

        // Recupera la bicicletta selezionata
        Optional<Bicicletta> biciclettaOptional = biciclettaRepository.findById(prenotazioneDTO.biciclettaId());
        if (biciclettaOptional.isEmpty()) {
            throw new IllegalArgumentException("Bicicletta non trovata");
        }
        Bicicletta bicicletta = biciclettaOptional.get();

        // Calcola il prezzo totale (numero di biciclette * prezzo del tour)
        BigDecimal totalePrezzo = tour.getPrice().multiply(BigDecimal.valueOf(prenotazioneDTO.numeroBiciclettePrenotate()));

        // Crea la nuova prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setTour(tour);
        prenotazione.setNumeroBiciclettePrenotate(prenotazioneDTO.numeroBiciclettePrenotate());
        prenotazione.setBicicletta(bicicletta);  // Assegna la bicicletta
        prenotazione.setTotalePrezzo(totalePrezzo);
        prenotazione.setDataPrenotazione(LocalDate.now());

        return prenotazioneRepository.save(prenotazione);
    }

    // Recupera tutte le prenotazioni di un determinato utente
    public List<Prenotazione> getPrenotazioniByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    // Recupera le prenotazioni cancellabili (data successiva a 4 giorni dalla prenotazione)
    public List<Prenotazione> getPrenotazioniCancellabili(Long utenteId) {
        LocalDate dataLimite = LocalDate.now().plusDays(4);
        return prenotazioneRepository.findByUtenteIdAndDataPrenotazioneAfter(utenteId, dataLimite);
    }

    // Cancella una prenotazione (cambia lo stato a "CANCELLATA")
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
