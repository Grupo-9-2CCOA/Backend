package school.sptech.projeto_extensao.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Setter
public class PeriodoFiltroDto {

    @Value("${app.timezone:America/Sao_Paulo}")
    private String timezone;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataInicio;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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
        return dataInicio.atZoneSameInstant(getZoneId()).toLocalDateTime();
    }

    public LocalDateTime getDataFim() {
        return dataFim.atZoneSameInstant(getZoneId()).toLocalDateTime();
    }

    @AssertTrue(message = "A data de início não pode ser posterior à data de fim.")
    public boolean isPeriodoValido() {
        return dataInicio == null || dataFim == null || !dataInicio.isAfter(dataFim);
    }

    private ZoneId getZoneId() {
        return ZoneId.of(timezone == null ? "America/Sao_Paulo" : timezone);
    }

    public Integer getMinimoPedidosFidelizacao() {
        return minimoPedidosFidelizacao;
    }
}
