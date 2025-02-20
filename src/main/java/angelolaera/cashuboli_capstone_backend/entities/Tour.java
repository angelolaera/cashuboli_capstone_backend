package angelolaera.cashuboli_capstone_backend.entities;

import angelolaera.cashuboli_capstone_backend.enums.TourType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tours")
@ToString
@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int maxParticipants;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private double lunghezzaItinerario;

    @Column(nullable = false)
    private String tempoMedioPercorrenza;

    @Column(nullable = false)
    private String linguaAccoglienza;

    @Column(nullable = false, length = 10000)
    private String descrizioneCompleta;

    @Column(nullable = true)
    private String accessoriInclusi;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bicicletta> biciclette;
}
