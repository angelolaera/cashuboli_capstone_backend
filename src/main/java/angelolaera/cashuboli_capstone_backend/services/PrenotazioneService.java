package angelolaera.cashuboli_capstone_backend.services;


import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import angelolaera.cashuboli_capstone_backend.repositories.PrenotazioneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public List<Prenotazione> getPrenotazioniByUtente(Long utenteId){
        return prenotazioneRepository.findUtenteById(utenteId);
    }

    public Prenotazione createPrenotazione (Prenotazione prenotazione){
        return prenotazioneRepository.save(prenotazione);
    }
}
