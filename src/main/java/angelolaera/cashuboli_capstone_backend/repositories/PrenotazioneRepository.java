package angelolaera.cashuboli_capstone_backend.repositories;

import angelolaera.cashuboli_capstone_backend.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findUtenteById(Long UtenteId);
}
