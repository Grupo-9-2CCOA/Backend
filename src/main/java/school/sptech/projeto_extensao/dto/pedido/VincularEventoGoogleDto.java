package school.sptech.projeto_extensao.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VincularEventoGoogleDto {
    @NotBlank
    private String eventoGoogleCalendarId;
}
