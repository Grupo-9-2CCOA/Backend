package school.sptech.projeto_extensao.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.projeto_extensao.dto.pedido.PeriodoFiltroDto;
import school.sptech.projeto_extensao.exception.RestExceptionHandler;
import school.sptech.projeto_extensao.service.RelatorioService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class RelatorioControllerTest {

    private static final String DATA_INICIO = "2026-01-01T00:00:00-03:00";
    private static final String DATA_FIM = "2026-01-31T23:59:59-03:00";

    @Mock
    private RelatorioService relatorioService;

    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        RelatorioController controller = new RelatorioController(relatorioService);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void deveUsarMinimoPadraoQuandoParametroNaoForInformado() throws Exception {
        mockMvc.perform(get("/relatorio")
                        .param("dataInicio", DATA_INICIO)
                        .param("dataFim", DATA_FIM))
                .andExpect(status().isOk());

        ArgumentCaptor<PeriodoFiltroDto> captor = ArgumentCaptor.forClass(PeriodoFiltroDto.class);
        verify(relatorioService).relatorioVendas(captor.capture());
        assertEquals(2, captor.getValue().getMinimoPedidosFidelizacao());
    }

    @Test
    void deveRepassarMinimoCustomizadoAoService() throws Exception {
        mockMvc.perform(get("/relatorio")
                        .param("dataInicio", DATA_INICIO)
                        .param("dataFim", DATA_FIM)
                        .param("minimoPedidosFidelizacao", "4"))
                .andExpect(status().isOk());

        ArgumentCaptor<PeriodoFiltroDto> captor = ArgumentCaptor.forClass(PeriodoFiltroDto.class);
        verify(relatorioService).relatorioVendas(captor.capture());
        assertEquals(4, captor.getValue().getMinimoPedidosFidelizacao());
    }

    @Test
    void deveRejeitarMinimoMenorQueUm() throws Exception {
        mockMvc.perform(get("/relatorio")
                        .param("dataInicio", DATA_INICIO)
                        .param("dataFim", DATA_FIM)
                        .param("minimoPedidosFidelizacao", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarMinimoNaoNumerico() throws Exception {
        mockMvc.perform(get("/relatorio")
                        .param("dataInicio", DATA_INICIO)
                        .param("dataFim", DATA_FIM)
                        .param("minimoPedidosFidelizacao", "invalido"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAceitarPeriodoSemMinimoNoEndpointDeCancelados() throws Exception {
        when(relatorioService.relatorioCancelados(any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/relatorio/cancelados")
                        .param("dataInicio", DATA_INICIO)
                        .param("dataFim", DATA_FIM))
                .andExpect(status().isOk());

        ArgumentCaptor<PeriodoFiltroDto> captor = ArgumentCaptor.forClass(PeriodoFiltroDto.class);
        verify(relatorioService).relatorioCancelados(captor.capture());
        assertEquals(2, captor.getValue().getMinimoPedidosFidelizacao());
    }
}
