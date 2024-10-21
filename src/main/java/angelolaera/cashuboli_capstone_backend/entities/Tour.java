package angelolaera.cashuboli_capstone_backend.entities;

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

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate date;  // Puoi anche considerare l'aggiunta di date di inizio e fine, se richiesto

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int maxParticipants;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bicicletta> biciclette;
}
