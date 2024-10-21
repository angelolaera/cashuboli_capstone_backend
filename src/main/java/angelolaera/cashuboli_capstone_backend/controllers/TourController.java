package angelolaera.cashuboli_capstone_backend.controllers;
import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.Payloads.TourDTO;
import angelolaera.cashuboli_capstone_backend.enums.TourType;
import angelolaera.cashuboli_capstone_backend.services.TourService;
import angelolaera.cashuboli_capstone_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    // Visualizza tutti i tour
    @GetMapping
    public List<TourDTO> getAllTours() {
        return tourService.getAllTours().stream()
                .map(tour -> new TourDTO(tour.getName(), tour.getDescription(), tour.getDate(), tour.getPrice(), tour.getMaxParticipants()))
                .collect(Collectors.toList());
    }

    // Crea un nuovo tour
    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody TourDTO tourDTO) {
        Tour tour = new Tour();
        tour.setName(tourDTO.name());
        tour.setDescription(tourDTO.description());
        tour.setDate(tourDTO.date());
        tour.setPrice(tourDTO.price());
        tour.setMaxParticipants(tourDTO.maxParticipants());

        Tour createdTour = tourService.createTour(tour);
        return ResponseEntity.ok(createdTour);
    }

    // Cancella un tour esistente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            tourService.deleteTour(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Aggiorna un tour esistente
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody TourDTO tourDTO) {
        try {
            Tour tour = new Tour();
            tour.setName(TourType.valueOf(String.valueOf(tourDTO.name())));
            tour.setDescription(tourDTO.description());
            tour.setDate(tourDTO.date());
            tour.setPrice(tourDTO.price());
            tour.setMaxParticipants(tourDTO.maxParticipants());

            Tour updatedTour = tourService.updateTour(id, tour);
            return ResponseEntity.ok(updatedTour);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Trova tour per data
    @GetMapping("/date/{date}")
    public ResponseEntity<List<TourDTO>> getToursByDate(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<TourDTO> tours = tourService.getToursByDate(parsedDate).stream()
                .map(tour -> new TourDTO(tour.getName(), tour.getDescription(), tour.getDate(), tour.getPrice(), tour.getMaxParticipants()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tours);
    }
}
