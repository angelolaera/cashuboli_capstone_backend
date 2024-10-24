package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.Payloads.BiciclettaDTO;
import angelolaera.cashuboli_capstone_backend.services.BiciclettaService;
import angelolaera.cashuboli_capstone_backend.exceptions.BadRequestException;
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
@RequestMapping("/api/biciclette")
public class BiciclettaController {

    @Autowired
    private BiciclettaService biciclettaService;

    // Visualizza tutte le biciclette
    @GetMapping
    public List<BiciclettaDTO> getAllBiciclette() {
        return biciclettaService.getAllBiciclette().stream()
                .map(bike -> new BiciclettaDTO(
                        bike.getModello(),
                        bike.getTipo(),
                        bike.isDisponibilita(),
                        bike.getImageUrl(),
                        bike.getId(),
                        bike.getDescrizione()))
                .collect(Collectors.toList());
    }

    // Crea una nuova bicicletta
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bicicletta> createBicicletta(@RequestBody BiciclettaDTO biciclettaDTO) {
        Bicicletta createdBicicletta = biciclettaService.createBicicletta(biciclettaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBicicletta);
    }

    // Carica l'immagine per una bicicletta
    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Bicicletta> uploadBiciclettaImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BadRequestException("Il file dell'immagine è obbligatorio.");
        }

        Bicicletta updatedBicicletta = biciclettaService.uploadImage(id, file);
        return ResponseEntity.ok(updatedBicicletta);
    }

    // Aggiorna una bicicletta esistente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Bicicletta> updateBicicletta(
            @PathVariable Long id,
            @RequestPart("bicicletta") BiciclettaDTO biciclettaDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        Bicicletta updatedBicicletta = biciclettaService.updateBicicletta(id, biciclettaDTO, image);
        return ResponseEntity.ok(updatedBicicletta);
    }

    // Cancella una bicicletta esistente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBicicletta(@PathVariable Long id) {
        biciclettaService.deleteBicicletta(id);
        return ResponseEntity.noContent().build();
    }
}
