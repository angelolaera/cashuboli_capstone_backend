package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")

public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/user/{utenteId}")
    public List<Prenotazione> getPrenotazioniByUtente(@PathVariable Long utenteId){
        return prenotazioneService.getPrenotazioniByUtente(utenteId);
    }

    @PostMapping
    public Prenotazione createPrenotazione(@RequestBody Prenotazione prenotazione){
        return prenotazioneService.createPrenotazione(prenotazione);
    }
}
