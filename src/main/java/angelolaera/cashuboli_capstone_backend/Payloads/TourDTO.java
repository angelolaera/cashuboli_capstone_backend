package angelolaera.cashuboli_capstone_backend.Payloads;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record TourDTO(
        @NotNull String name,
        @NotNull String description,
        @NotNull BigDecimal price,
        @NotNull int maxParticipants,
        @NotNull Long id,
        String imageUrl
) {}

