package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.Payloads.PrenotazioneDTO;
import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.enums.StatoPrenotazione;
import angelolaera.cashuboli_capstone_backend.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Prenotazione>> getAllPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneService.getAllPrenotazioni();
        return ResponseEntity.ok(prenotazioni);
    }

    // Ottiene tutte le prenotazioni dell'utente autenticato
    @GetMapping("/user/{utenteId}")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniByUtente(@PathVariable Long utenteId) {
        List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniByUtente(utenteId);
        if (prenotazioni.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prenotazioni);
    }

    // Crea una nuova prenotazione
    @PostMapping
    public ResponseEntity<Prenotazione> createPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO, @AuthenticationPrincipal Utente utente) {
        try {
            Prenotazione nuovaPrenotazione = prenotazioneService.createPrenotazione(prenotazioneDTO, utente);
            return ResponseEntity.ok(nuovaPrenotazione);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Cancella una prenotazione (cambia lo stato a "CANCELLATA")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaPrenotazione(@PathVariable Long id) {
        try {
            prenotazioneService.cancellaPrenotazione(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Aggiorna lo stato della prenotazione (Conferma prenotazione)
    @PutMapping("/{id}")
    public ResponseEntity<Prenotazione> confermaPrenotazione(@PathVariable Long id) {
        try {
            Prenotazione prenotazioneAggiornata = prenotazioneService.confermaPrenotazione(id);
            return ResponseEntity.ok(prenotazioneAggiornata);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
