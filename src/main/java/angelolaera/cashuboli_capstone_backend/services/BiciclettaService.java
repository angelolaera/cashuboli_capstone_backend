package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.repositories.BiciclettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiciclettaService {

    @Autowired
    private BiciclettaRepository biciclettaRepository;

    // Ottieni tutte le biciclette
    public List<Bicicletta> getAllBiciclette() {
        return biciclettaRepository.findAll();
    }

    // Crea una nuova bicicletta
    public Bicicletta createBicicletta(Bicicletta bicicletta) {
        return biciclettaRepository.save(bicicletta);
    }

    // Aggiorna una bicicletta esistente
    public Bicicletta updateBicicletta(Long id, Bicicletta biciclettaDetails) {
        Optional<Bicicletta> optionalBicicletta = biciclettaRepository.findById(id);
        if (optionalBicicletta.isPresent()) {
            Bicicletta existingBicicletta = optionalBicicletta.get();
            existingBicicletta.setModello(biciclettaDetails.getModello());
            existingBicicletta.setTipo(biciclettaDetails.getTipo());
            existingBicicletta.setDisponibilita(biciclettaDetails.isDisponibilita());
            return biciclettaRepository.save(existingBicicletta);
        } else {
            throw new IllegalArgumentException("Bicicletta non trovata");
        }
    }

    // Cancella una bicicletta
    public void deleteBicicletta(Long id) {
        biciclettaRepository.deleteById(id);
    }

    // Ottieni una bicicletta specifica
    public Bicicletta getBiciclettaById(Long id) {
        return biciclettaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bicicletta non trovata"));
    }

    // Ottieni tutte le biciclette disponibili
    public List<Bicicletta> getBicicletteDisponibili() {
        return biciclettaRepository.findByDisponibilitaTrue();
    }
}
