package angelolaera.cashuboli_capstone_backend.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotNull(message = "Il nome non deve essere nullo")
        @Size(min = 2, message = "Il nome deve avere almeno 2 caratteri")
        String nome,

        @NotNull(message = "Il cognome non deve essere nullo")
        @Size(min = 2, message = "Il cognome deve avere almeno 2 caratteri")
        String cognome,

        @NotNull(message = "La data di nascita non deve essere nulla")
        String data_di_nascita,

        @NotNull(message = "L'email non deve essere nulla")
        @Email(message = "Deve essere un'email valida")
        String email,

        @NotNull(message = "La password non deve essere nulla")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
        String password
) {}
