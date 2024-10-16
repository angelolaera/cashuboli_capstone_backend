package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TourService {
    @Autowired
    private TourRepository tourRepository;

    public List<Tour> getAllTours(){
        return tourRepository.findAll();
    }

    public Tour createTour(Tour tour){
        return tourRepository.save(tour);
    }

    public void deleteTour (Long id){
        tourRepository.deleteById(id);
    }
}

