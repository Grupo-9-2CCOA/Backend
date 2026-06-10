package school.sptech.projeto_extensao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoCalendarDto {

    @NotBlank(message = "O título do evento é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de término é obrigatória")
    private LocalDateTime dataFim;
}
