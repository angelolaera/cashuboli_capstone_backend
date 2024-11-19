package angelolaera.cashuboli_capstone_backend.entities;

import angelolaera.cashuboli_capstone_backend.enums.StatoPrenotazione;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prenotazioni")
@ToString

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "bicicletta_id", nullable = false)
    private Bicicletta bicicletta;

    @Column(nullable = false)
    private int numeroBiciclettePrenotate;

    @Column(nullable = false)
    private LocalDate dataPrenotazione;

    @Column(nullable = false)
    private BigDecimal totalePrezzo;

    @Enumerated(EnumType.STRING) // Aggiungi questo se non presente
    private StatoPrenotazione stato;  // Campo stato per memorizzare lo stato della prenotazione
}


