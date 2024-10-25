package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.Payloads.TourDTO;
import angelolaera.cashuboli_capstone_backend.exceptions.BadRequestException;
import angelolaera.cashuboli_capstone_backend.exceptions.NotFoundException;
import angelolaera.cashuboli_capstone_backend.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
                .map(tour -> new TourDTO(
                        tour.getName(),
                        tour.getDescription(),
                        tour.getPrice(),
                        tour.getMaxParticipants(),
                        tour.getId(),
                        tour.getImageUrl(),
                        tour.getLunghezzaItinerario(),
                        tour.getTempoMedioPercorrenza(),
                        tour.getLinguaAccoglienza(),
                        tour.getDescrizioneCompleta(),
                        tour.getAccessoriInclusi()
                ))
                .collect(Collectors.toList());
    }

    // Crea un nuovo tour con immagine
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tour> createTour(@RequestBody TourDTO tourDTO) {
        Tour tour = new Tour();
        tour.setName(tourDTO.name());
        tour.setDescription(tourDTO.description());
        tour.setPrice(tourDTO.price());
        tour.setMaxParticipants(tourDTO.maxParticipants());
        tour.setLunghezzaItinerario(tourDTO.lunghezzaItinerario());
        tour.setTempoMedioPercorrenza(tourDTO.tempoMedioPercorrenza());
        tour.setLinguaAccoglienza(tourDTO.linguaAccoglienza());
        tour.setDescrizioneCompleta(tourDTO.descrizioneCompleta());
        tour.setAccessoriInclusi(tourDTO.accessoriInclusi());
        Tour createdTour = tourService.save(tour);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTour);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Tour> uploadTourImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BadRequestException("Il file dell'immagine è obbligatorio.");
        }

        Tour updatedTour = tourService.uploadImage(id, file);
        return ResponseEntity.ok(updatedTour);
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
    public ResponseEntity<Tour> updateTour(
            @PathVariable Long id,
            @RequestPart("tour") TourDTO tourDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        Tour tour = new Tour();
        tour.setName(tourDTO.name());
        tour.setDescription(tourDTO.description());
        tour.setPrice(tourDTO.price());
        tour.setMaxParticipants(tourDTO.maxParticipants());
        tour.setLunghezzaItinerario(tourDTO.lunghezzaItinerario());
        tour.setTempoMedioPercorrenza(tourDTO.tempoMedioPercorrenza());
        tour.setLinguaAccoglienza(tourDTO.linguaAccoglienza());
        tour.setDescrizioneCompleta(tourDTO.descrizioneCompleta());
        tour.setAccessoriInclusi(tourDTO.accessoriInclusi());

        // Passa l'immagine al service per la gestione
        Tour updatedTour = tourService.updateTour(id, tour, image);
        return ResponseEntity.ok(updatedTour);
    }
}
