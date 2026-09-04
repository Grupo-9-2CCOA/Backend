package school.sptech.projeto_extensao.mapper;

import org.junit.jupiter.api.Test;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.model.Pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PedidoMapperTest {

    @Test
    void deveAdicionarIdentificadorNaResposta() {
        Pedido pedido = new Pedido();
        pedido.setId(10);

        PedidoResponseDto resposta = PedidoMapper.toDto(pedido);

        assertEquals(10, resposta.getId());
    }
}
