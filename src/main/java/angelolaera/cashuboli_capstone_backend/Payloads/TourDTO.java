package angelolaera.cashuboli_capstone_backend.Payloads;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TourDTO(
        angelolaera.cashuboli_capstone_backend.enums.TourType name,
        @NotNull String description,
        @NotNull LocalDate date,
        @NotNull BigDecimal price,
        @NotNull int maxParticipants
) {}

