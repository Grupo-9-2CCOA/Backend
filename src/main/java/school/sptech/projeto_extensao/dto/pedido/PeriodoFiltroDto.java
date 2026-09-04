package school.sptech.projeto_extensao.dto.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PeriodoFiltroDto {

    @NotNull
    private OffsetDateTime dataInicio;

    @NotNull
    private OffsetDateTime dataFim;

    public LocalDateTime getDataInicio() {
        return dataInicio.toLocalDateTime();
    }

    public LocalDateTime getDataFim() {
        return dataFim.toLocalDateTime();
    }
}
