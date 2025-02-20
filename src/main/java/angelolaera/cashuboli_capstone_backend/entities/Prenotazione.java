package angelolaera.cashuboli_capstone_backend.entities;

import angelolaera.cashuboli_capstone_backend.enums.StatoPrenotazione;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "prenotazioni")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 🚀 Evita errori di serializzazione
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

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione stato;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column (columnDefinition = "TEXT")
    private String informazioniAggiuntive;
}
