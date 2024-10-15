package angelolaera.cashuboli_capstone_backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="biciclette")
@ToString

@Entity
public class Bicicletta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String modello;
    private String tipo;  // e-bike, standard, per disabili
    private boolean disponibilita;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;


}
