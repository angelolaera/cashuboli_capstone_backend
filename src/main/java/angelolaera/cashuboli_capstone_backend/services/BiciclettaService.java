package angelolaera.cashuboli_capstone_backend.services;

import angelolaera.cashuboli_capstone_backend.entities.Bicicletta;
import angelolaera.cashuboli_capstone_backend.Payloads.BiciclettaDTO;
import angelolaera.cashuboli_capstone_backend.repositories.BiciclettaRepository;
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
public class BiciclettaService {

    @Autowired
    private BiciclettaRepository biciclettaRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Restituisce tutte le biciclette
    public List<Bicicletta> getAllBiciclette() {
        return biciclettaRepository.findAll();
    }

    // Crea una nuova bicicletta
    public Bicicletta createBicicletta(BiciclettaDTO biciclettaDTO) {
        Bicicletta bicicletta = new Bicicletta();
        bicicletta.setModello(biciclettaDTO.modello());
        bicicletta.setTipo(biciclettaDTO.tipo());
        bicicletta.setDisponibilita(biciclettaDTO.disponibilita());
        bicicletta.setDescrizione(biciclettaDTO.descrizione());
        return biciclettaRepository.save(bicicletta);
    }

    // Aggiorna una bicicletta esistente
    public Bicicletta updateBicicletta(Long id, BiciclettaDTO biciclettaDTO, MultipartFile image) throws IOException {
        Optional<Bicicletta> optionalBicicletta = biciclettaRepository.findById(id);

        if (optionalBicicletta.isEmpty()) {
            throw new NotFoundException("Bicicletta con ID " + id + " non trovata.");
        }

        Bicicletta existingBicicletta = optionalBicicletta.get();
        existingBicicletta.setModello(biciclettaDTO.modello());
        existingBicicletta.setTipo(biciclettaDTO.tipo());
        existingBicicletta.setDisponibilita(biciclettaDTO.disponibilita());

        // Carica l'immagine se viene fornita
        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            existingBicicletta.setImageUrl(imageUrl);
        }

        return biciclettaRepository.save(existingBicicletta);
    }

    // Carica l'immagine per una bicicletta esistente
    public Bicicletta uploadImage(Long id, MultipartFile file) throws IOException {
        Optional<Bicicletta> optionalBicicletta = biciclettaRepository.findById(id);

        if (optionalBicicletta.isEmpty()) {
            throw new NotFoundException("Bicicletta con ID " + id + " non trovata.");
        }

        Bicicletta bicicletta = optionalBicicletta.get();

        // Carica l'immagine su Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        // Aggiorna l'URL dell'immagine nella bicicletta
        bicicletta.setImageUrl(imageUrl);

        return biciclettaRepository.save(bicicletta);
    }

    // Cancella una bicicletta
    public void deleteBicicletta(Long id) {
        if (!biciclettaRepository.existsById(id)) {
            throw new NotFoundException("Bicicletta con ID " + id + " non trovata.");
        }
        biciclettaRepository.deleteById(id);
    }
}
