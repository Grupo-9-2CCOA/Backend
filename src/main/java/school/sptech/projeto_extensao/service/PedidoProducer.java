package school.sptech.projeto_extensao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.Config.RabbitMQConfig;
import school.sptech.projeto_extensao.dto.CalendarCommand;
import school.sptech.projeto_extensao.model.Pedido;

@Service
public class PedidoProducer {

    private static final Logger logger = LoggerFactory.getLogger(PedidoProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarCriacao(Pedido pedido) {
        publicar("create", RabbitMQConfig.ROUTING_KEY, pedido);
    }

    public void publicarAtualizacao(Pedido pedido) {
        publicar("update", RabbitMQConfig.ROUTING_KEY_UPDATE, pedido);
    }

    public void publicarExclusao(Pedido pedido) {
        publicar("delete", RabbitMQConfig.ROUTING_KEY_DELETE, pedido);
    }

    private void publicar(String tipo, String routingKey, Pedido pedido) {
        try {
            CalendarCommand command = new CalendarCommand(
                    pedido.getId(),
                    tipo,
                    pedido.getProduto(),
                    pedido.getDescricao(),
                    pedido.getDataPedido(),
                    pedido.getDataPedido() != null ? pedido.getDataPedido().plusHours(1) : null,
                    pedido.getEventoGoogleCalendarId()
            );
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, command);
            logger.info("Comando de calendario '{}' publicado para o pedido {}", tipo, pedido.getId());
        } catch (Exception e) {
            logger.warn("Falha ao publicar comando de calendario '{}' do pedido {}: {}", tipo, pedido.getId(), e.getMessage());
        }
    }
}
