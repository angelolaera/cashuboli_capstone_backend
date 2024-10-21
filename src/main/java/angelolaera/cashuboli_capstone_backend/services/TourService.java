package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.repositories.TourRepository;
import angelolaera.cashuboli_capstone_backend.exceptions.NotFoundException;  // Assumendo che tu abbia una eccezione personalizzata
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    // Restituisce tutti i tour
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    // Crea un nuovo tour
    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    // Trova tour per data
    public List<Tour> getToursByDate(LocalDate date) {
        return tourRepository.findByDate(date);
    }


    // Cancella un tour
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new NotFoundException("Tour con ID " + id + " non trovato.");
        }
        tourRepository.deleteById(id);
    }

    // Aggiorna un tour esistente
    public Tour updateTour(Long id, Tour tourDetails) {
        Optional<Tour> optionalTour = tourRepository.findById(id);

        if (optionalTour.isEmpty()) {
            throw new NotFoundException("Tour con ID " + id + " non trovato.");
        }

        Tour existingTour = optionalTour.get();
        existingTour.setName(tourDetails.getName());
        existingTour.setDescription(tourDetails.getDescription());
        existingTour.setDate(tourDetails.getDate());
        existingTour.setPrice(tourDetails.getPrice());
        existingTour.setMaxParticipants(tourDetails.getMaxParticipants());

        return tourRepository.save(existingTour);
    }
}
