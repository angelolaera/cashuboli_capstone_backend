package angelolaera.cashuboli_capstone_backend.repositories;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.enums.TourType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour,Long> {
    List<Tour> findByName (String name);
}
