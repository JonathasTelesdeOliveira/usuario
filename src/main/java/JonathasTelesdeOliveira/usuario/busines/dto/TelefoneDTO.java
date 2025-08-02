package JonathasTelesdeOliveira.usuario.busines.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class TelefoneDTO {
    private long id;
    private String numero;
    private String ddd;
}
