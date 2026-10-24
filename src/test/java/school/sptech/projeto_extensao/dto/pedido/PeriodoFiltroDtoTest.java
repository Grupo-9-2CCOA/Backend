package school.sptech.projeto_extensao.dto.pedido;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeriodoFiltroDtoTest {

    @Test
    void deveConverterDatasParaTimezoneConfigurado() {
        PeriodoFiltroDto periodo = new PeriodoFiltroDto(
                OffsetDateTime.parse("2030-01-15T10:00:00Z"),
                OffsetDateTime.parse("2030-01-15T20:00:00Z")
        );
        periodo.setTimezone("America/Sao_Paulo");

        assertEquals(LocalDateTime.of(2030, 1, 15, 7, 0), periodo.getDataInicio());
        assertEquals(LocalDateTime.of(2030, 1, 15, 17, 0), periodo.getDataFim());
    }

    @Test
    void deveUsarTimezonePadraoQuandoNaoConfigurado() {
        PeriodoFiltroDto periodo = new PeriodoFiltroDto(
                OffsetDateTime.parse("2030-01-15T10:00:00Z"),
                OffsetDateTime.parse("2030-01-15T20:00:00Z")
        );

        assertEquals(LocalDateTime.of(2030, 1, 15, 7, 0), periodo.getDataInicio());
    }

    @Test
    void deveValidarPeriodo() {
        PeriodoFiltroDto valido = new PeriodoFiltroDto(
                OffsetDateTime.parse("2030-01-15T10:00:00Z"),
                OffsetDateTime.parse("2030-01-15T20:00:00Z")
        );
        PeriodoFiltroDto invalido = new PeriodoFiltroDto(
                OffsetDateTime.parse("2030-01-16T10:00:00Z"),
                OffsetDateTime.parse("2030-01-15T20:00:00Z")
        );

        assertTrue(valido.isPeriodoValido());
        assertFalse(invalido.isPeriodoValido());
    }
}
