package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Tour;
import angelolaera.cashuboli_capstone_backend.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")

public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping
    public List<Tour> getAllTours(){
        return tourService.getAllTours();
    }

   @PostMapping
   public Tour createTour(@RequestBody Tour tour){
        return tourService.createTour(tour);
   }

   @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id){
        tourService.deleteTour(id);
   }
}
