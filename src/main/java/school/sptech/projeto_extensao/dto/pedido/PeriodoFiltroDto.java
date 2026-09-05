package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Setter
public class PeriodoFiltroDto {

    @NotNull
    private OffsetDateTime dataInicio;

    @NotNull
    private OffsetDateTime dataFim;

    @NotNull
    @Min(1)
    @Schema(description = "Quantidade mínima de pedidos para considerar um cliente fidelizado", example = "2", defaultValue = "2")
    private Integer minimoPedidosFidelizacao = 2;

    public PeriodoFiltroDto(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public PeriodoFiltroDto(OffsetDateTime dataInicio, OffsetDateTime dataFim, Integer minimoPedidosFidelizacao) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.minimoPedidosFidelizacao = minimoPedidosFidelizacao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio.toLocalDateTime();
    }

    public LocalDateTime getDataFim() {
        return dataFim.toLocalDateTime();
    }

    public Integer getMinimoPedidosFidelizacao() {
        return minimoPedidosFidelizacao;
    }
}
