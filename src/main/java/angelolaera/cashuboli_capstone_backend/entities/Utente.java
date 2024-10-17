package angelolaera.cashuboli_capstone_backend.entities;

import angelolaera.cashuboli_capstone_backend.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="utenti")
public class Utente implements UserDetails {

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

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo = Ruolo.USER;

    // Metodi di UserDetails

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.ruolo.name()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;  // Usare l'email come nome utente per l'autenticazione
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;  // Implementa logica di scadenza account se necessaria
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;  // Implementa logica di blocco account se necessaria
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;  // Implementa logica se necessario
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;  // Implementa logica di abilitazione se hai un campo per disabilitare l'utente
    }
}
