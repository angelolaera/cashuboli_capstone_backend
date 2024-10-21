package angelolaera.cashuboli_capstone_backend.controllers;

import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.services.BiciclettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biciclette")
public class BiciclettaController {

    @Autowired
    private BiciclettaService biciclettaService;

    // Ottieni tutte le biciclette
    @GetMapping
    public ResponseEntity<List<Bicicletta>> getAllBiciclette() {
        List<Bicicletta> biciclette = biciclettaService.getAllBiciclette();
        return ResponseEntity.ok(biciclette);
    }

    // Crea una nuova bicicletta
    @PostMapping
    public ResponseEntity<Bicicletta> createBicicletta(@RequestBody Bicicletta bicicletta) {
        Bicicletta nuovaBicicletta = biciclettaService.createBicicletta(bicicletta);
        return ResponseEntity.ok(nuovaBicicletta);
    }

    // Aggiorna una bicicletta esistente
    @PutMapping("/{id}")
    public ResponseEntity<Bicicletta> updateBicicletta(@PathVariable Long id, @RequestBody Bicicletta biciclettaDetails) {
        Bicicletta updatedBicicletta = biciclettaService.updateBicicletta(id, biciclettaDetails);
        return ResponseEntity.ok(updatedBicicletta);
    }

    // Cancella una bicicletta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBicicletta(@PathVariable Long id) {
        biciclettaService.deleteBicicletta(id);
        return ResponseEntity.noContent().build();
    }

    // Ottieni una bicicletta specifica
    @GetMapping("/{id}")
    public ResponseEntity<Bicicletta> getBiciclettaById(@PathVariable Long id) {
        Bicicletta bicicletta = biciclettaService.getBiciclettaById(id);
        return ResponseEntity.ok(bicicletta);
    }

    // Ottieni tutte le biciclette disponibili
    @GetMapping("/disponibili")
    public ResponseEntity<List<Bicicletta>> getBicicletteDisponibili() {
        List<Bicicletta> bicicletteDisponibili = biciclettaService.getBicicletteDisponibili();
        return ResponseEntity.ok(bicicletteDisponibili);
    }
}
