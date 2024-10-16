package angelolaera.cashuboli_capstone_backend.entities;

import angelolaera.cashuboli_capstone_backend.enums.Ruolo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="utenti")
@ToString

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String data_di_nascita;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo = Ruolo.USER;

}
