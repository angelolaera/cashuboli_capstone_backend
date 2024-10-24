package angelolaera.cashuboli_capstone_backend.Payloads;

import jakarta.validation.constraints.NotNull;

public record BiciclettaDTO(
        @NotNull String modello,
        @NotNull String tipo,
        @NotNull boolean disponibilita,
        String imageUrl, // Campo per l'URL dell'immagine
        Long id, // Campo ID opzionale
        @NotNull String descrizione
) {}
