package angelolaera.cashuboli_capstone_backend.Payloads;

import jakarta.validation.constraints.NotNull;


public record PrenotazioneDTO(
        @NotNull Long tourId,
        @NotNull int numeroBiciclettePrenotate,
        @NotNull Long biciclettaId  // Id della bicicletta selezionata
) {}

