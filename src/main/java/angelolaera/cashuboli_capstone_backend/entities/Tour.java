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
    @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;
    private String description;
    private LocalDate date;
    private BigDecimal price;
    private int maxParticipants;

    @OneToMany(mappedBy = "tour")
    private List<Bicicletta> biciclette;


}
