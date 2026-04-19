package school.sptech.projeto_extensao.controller.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Período de tempo para filtro do relatório")
public enum PeriodoFiltro {

    @Schema(description = "Últimos 7 dias")
    SEMANA,

    @Schema(description = "Último mês")
    MENSAL,

    @Schema(description = "Últimos 6 meses")
    SEMESTRAL,

    @Schema(description = "Último ano")
    ANUAL
}