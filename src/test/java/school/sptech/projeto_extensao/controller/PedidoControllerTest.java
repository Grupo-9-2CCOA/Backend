package school.sptech.projeto_extensao.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.exception.RestExceptionHandler;
import school.sptech.projeto_extensao.exception.StatusPedidoInvalidoException;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.service.GoogleCalendarService;
import school.sptech.projeto_extensao.service.PedidoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private GoogleCalendarService googleCalendarService;

    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        PedidoController controller = new PedidoController(pedidoService, googleCalendarService);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve atualizar os status e retornar o pedido")
    void deveAtualizarStatus() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        Mockito.when(pedidoService.atualizarStatus(1, 2, 2)).thenReturn(pedido);

        mockMvc.perform(patch("/pedidos/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pagamentoId": 2,
                                  "entregaId": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Deve retornar 400 quando os status não forem informados")
    void deveRejeitarStatusNaoInformados() throws Exception {
        mockMvc.perform(patch("/pedidos/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.pagamentoId").exists())
                .andExpect(jsonPath("$.entregaId").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o corpo for inválido")
    void deveRejeitarCorpoInvalido() throws Exception {
        mockMvc.perform(patch("/pedidos/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Corpo da requisição inválido."));
    }

    @Test
    @DisplayName("Deve retornar 400 quando o status cancelado for solicitado")
    void deveRejeitarStatusCancelado() throws Exception {
        Mockito.when(pedidoService.atualizarStatus(1, 3, 1))
                .thenThrow(new StatusPedidoInvalidoException("Utilize o cancelamento do pedido."));

        mockMvc.perform(patch("/pedidos/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pagamentoId": 3,
                                  "entregaId": 1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Utilize o cancelamento do pedido."));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o pedido não for encontrado")
    void deveRejeitarPedidoInexistente() throws Exception {
        Mockito.when(pedidoService.atualizarStatus(99, 1, 1))
                .thenThrow(new EntidadeNaoEncontradaException("Pedido não encontrado."));

        mockMvc.perform(patch("/pedidos/99/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pagamentoId": 1,
                                  "entregaId": 1
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pedido não encontrado."));
    }
}
