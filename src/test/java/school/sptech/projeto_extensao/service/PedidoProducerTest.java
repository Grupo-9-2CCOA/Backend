package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import school.sptech.projeto_extensao.Config.RabbitMQConfig;
import school.sptech.projeto_extensao.dto.CalendarCommand;
import school.sptech.projeto_extensao.model.Pedido;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class PedidoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoProducer producer;

    private Pedido montarPedido() {
        return new Pedido(
                1,
                "Bolo 5kg",
                "1 Bolo de kg decorado",
                59.99,
                true,
                false,
                LocalDateTime.of(2026, 10, 10, 14, 0),
                null,
                null,
                null,
                null,
                null,
                "evento-123"
        );
    }

    @Test
    @DisplayName("Deve publicar comando de criação com routing key e payload corretos")
    void devePublicarCriacao() {
        Pedido pedido = montarPedido();

        producer.publicarCriacao(pedido);

        ArgumentCaptor<CalendarCommand> captor = ArgumentCaptor.forClass(CalendarCommand.class);
        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(
                        Mockito.eq(RabbitMQConfig.EXCHANGE),
                        Mockito.eq(RabbitMQConfig.ROUTING_KEY),
                        captor.capture()
                );

        CalendarCommand command = captor.getValue();
        Assertions.assertEquals(1, command.getPedidoId());
        Assertions.assertEquals("create", command.getTipo());
        Assertions.assertEquals("Bolo 5kg", command.getProduto());
        Assertions.assertEquals("1 Bolo de kg decorado", command.getDescricao());
        Assertions.assertEquals(LocalDateTime.of(2026, 10, 10, 14, 0), command.getDataInicio());
        Assertions.assertEquals(LocalDateTime.of(2026, 10, 10, 15, 0), command.getDataFim());
        Assertions.assertEquals("evento-123", command.getEventoGoogleCalendarId());
    }

    @Test
    @DisplayName("Deve publicar comando de atualização com routing key de update")
    void devePublicarAtualizacao() {
        Pedido pedido = montarPedido();

        producer.publicarAtualizacao(pedido);

        ArgumentCaptor<CalendarCommand> captor = ArgumentCaptor.forClass(CalendarCommand.class);
        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(
                        Mockito.eq(RabbitMQConfig.EXCHANGE),
                        Mockito.eq(RabbitMQConfig.ROUTING_KEY_UPDATE),
                        captor.capture()
                );

        Assertions.assertEquals("update", captor.getValue().getTipo());
    }

    @Test
    @DisplayName("Deve publicar comando de exclusão com routing key de delete")
    void devePublicarExclusao() {
        Pedido pedido = montarPedido();

        producer.publicarExclusao(pedido);

        ArgumentCaptor<CalendarCommand> captor = ArgumentCaptor.forClass(CalendarCommand.class);
        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(
                        Mockito.eq(RabbitMQConfig.EXCHANGE),
                        Mockito.eq(RabbitMQConfig.ROUTING_KEY_DELETE),
                        captor.capture()
                );

        Assertions.assertEquals("delete", captor.getValue().getTipo());
    }

    @Test
    @DisplayName("Não deve propagar exceção quando o broker está indisponível")
    void naoDevePropagarExcecaoQuandoBrokerIndisponivel() {
        Pedido pedido = montarPedido();
        Mockito.doThrow(new RuntimeException("broker indisponível"))
                .when(rabbitTemplate)
                .convertAndSend(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(Object.class)
                );

        Assertions.assertDoesNotThrow(() -> producer.publicarCriacao(pedido));

        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(Object.class)
                );
    }
}
