package angelolaera.cashuboli_capstone_backend.controllers;


import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.services.TourService;
import angelolaera.cashuboli_capstone_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    // Visualizza tutti i tour
    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    // Crea un nuovo tour
    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
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
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour tourDetails) {
        try {
            Tour updatedTour = tourService.updateTour(id, tourDetails);
            return ResponseEntity.ok(updatedTour);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Trova tour per data
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Tour>> getToursByDate(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Tour> tours = tourService.getToursByDate(parsedDate);
        return ResponseEntity.ok(tours);
    }

}
