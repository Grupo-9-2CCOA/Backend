package school.sptech.projeto_extensao.dto.pedido;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class PeriodoFiltroDto {

    @NotNull
    private OffsetDateTime dataInicio;

    @NotNull
    private OffsetDateTime dataFim;

    public PeriodoFiltroDto() {}

    public PeriodoFiltroDto(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio.toLocalDateTime();
    }

    public LocalDateTime getDataFim() {
        return dataFim.toLocalDateTime();
    }

    public void setDataInicio(OffsetDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(OffsetDateTime dataFim) {
        this.dataFim = dataFim;
    }
}