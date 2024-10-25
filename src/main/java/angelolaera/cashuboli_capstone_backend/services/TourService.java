package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.repositories.TourRepository;
import angelolaera.cashuboli_capstone_backend.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Restituisce tutti i tour
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour save(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour uploadImage(Long id, MultipartFile file) throws IOException {
        Optional<Tour> optionalTour = tourRepository.findById(id);

        if (optionalTour.isEmpty()) {
            throw new NotFoundException("Tour con ID " + id + " non trovato.");
        }

        Tour tour = optionalTour.get();

        // Caricamento immagine su Cloudinary (o qualsiasi servizio usi)
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        // Aggiorna l'URL dell'immagine nel tour
        tour.setImageUrl(imageUrl);

        return tourRepository.save(tour);
    }

    // Cancella un tour
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new NotFoundException("Tour con ID " + id + " non trovato.");
        }
        tourRepository.deleteById(id);
    }

    // Aggiorna un tour esistente
    public Tour updateTour(Long id, Tour tourDetails, MultipartFile image) throws IOException {
        Optional<Tour> optionalTour = tourRepository.findById(id);

        if (optionalTour.isEmpty()) {
            throw new NotFoundException("Tour con ID " + id + " non trovato.");
        }

        Tour existingTour = optionalTour.get();
        existingTour.setName(tourDetails.getName());
        existingTour.setDescription(tourDetails.getDescription());
        existingTour.setPrice(tourDetails.getPrice());
        existingTour.setMaxParticipants(tourDetails.getMaxParticipants());
        existingTour.setLunghezzaItinerario(tourDetails.getLunghezzaItinerario());
        existingTour.setTempoMedioPercorrenza(tourDetails.getTempoMedioPercorrenza());
        existingTour.setLinguaAccoglienza(tourDetails.getLinguaAccoglienza());
        existingTour.setDescrizioneCompleta(tourDetails.getDescrizioneCompleta());
        existingTour.setAccessoriInclusi(tourDetails.getAccessoriInclusi());

        // Aggiorna l'immagine se è stata fornita nel controller
        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            existingTour.setImageUrl(imageUrl);
        }

        return tourRepository.save(existingTour); // Salva il tour aggiornato
    }
}
