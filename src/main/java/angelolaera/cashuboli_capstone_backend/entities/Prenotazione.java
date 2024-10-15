package angelolaera.cashuboli_capstone_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="prenotazioni")
@ToString

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private int numeroBiciclettePrenotate;
    private LocalDate dataPrenotazione;
    private BigDecimal totalePrezzo;

}
