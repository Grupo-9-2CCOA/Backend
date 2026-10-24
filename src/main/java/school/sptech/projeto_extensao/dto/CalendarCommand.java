package school.sptech.projeto_extensao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarCommand {

    private Integer pedidoId;

    private String tipo;

    private String produto;

    private String descricao;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String eventoGoogleCalendarId;
}
