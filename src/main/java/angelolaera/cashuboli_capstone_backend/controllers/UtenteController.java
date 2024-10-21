package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Utente;
import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    // Visualizza le prenotazioni dell'utente autenticato
    @GetMapping("/prenotazioni")
    public ResponseEntity<List<Prenotazione>> getPrenotazioni(@AuthenticationPrincipal Utente utente) {
        List<Prenotazione> prenotazioni = utenteService.getPrenotazioni(utente.getId());
        return ResponseEntity.ok(prenotazioni);
    }

    // Visualizza solo le prenotazioni che possono essere cancellate
    @GetMapping("/prenotazioni/cancellabili")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniCancellabili(@AuthenticationPrincipal Utente utente) {
        List<Prenotazione> prenotazioniCancellabili = utenteService.getPrenotazioniCancellabili(utente.getId());
        return ResponseEntity.ok(prenotazioniCancellabili);
    }

    // Elimina una prenotazione se è cancellabile
    @DeleteMapping("/prenotazioni/{prenotazioneId}")
    public ResponseEntity<Void> deletePrenotazione(@PathVariable Long prenotazioneId, @AuthenticationPrincipal Utente utente) {
        List<Prenotazione> prenotazioniCancellabili = utenteService.getPrenotazioniCancellabili(utente.getId());
        boolean cancellabile = prenotazioniCancellabili.stream().anyMatch(p -> p.getId().equals(prenotazioneId));

        if (cancellabile) {
            utenteService.deletePrenotazione(prenotazioneId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build(); // Non consentito se non cancellabile
        }
    }

    // Aggiorna il profilo dell'utente (nome, cognome, password)
    @PutMapping("/profilo")
    public ResponseEntity<Utente> updateProfilo(@AuthenticationPrincipal Utente utente, @RequestBody Utente profiloAggiornato) {
        Utente utenteAggiornato = utenteService.updateProfilo(utente, profiloAggiornato.getPassword());
        return ResponseEntity.ok(utenteAggiornato);
    }

    // Elimina l'account dell'utente
    @DeleteMapping("/profilo")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal Utente utente) {
        utenteService.deleteUtente(utente.getId());
        return ResponseEntity.noContent().build();
    }
}
