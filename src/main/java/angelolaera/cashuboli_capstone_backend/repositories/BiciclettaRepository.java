package angelolaera.cashuboli_capstone_backend.repositories;

import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiciclettaRepository extends JpaRepository<Bicicletta, Long> {

    // Metodo per trovare tutte le biciclette associate a un tour specifico
    List<Bicicletta> findByTourId(Long tourId);

    // Metodo per trovare tutte le biciclette disponibili
    List<Bicicletta> findByDisponibilitaTrue();
}
