package school.sptech.projeto_extensao.dto.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeriodoFiltroDto {

    @NotNull
    private OffsetDateTime dataInicio;

    @NotNull
    private OffsetDateTime dataFim;
}