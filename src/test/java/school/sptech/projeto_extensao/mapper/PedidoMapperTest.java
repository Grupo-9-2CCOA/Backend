package school.sptech.projeto_extensao.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.model.Pedido;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PedidoMapperTest {

    @Test
    void deveAdicionarIdentificadorNaResposta() {
        Pedido pedido = new Pedido();
        pedido.setId(10);

        PedidoResponseDto resposta = PedidoMapper.toDto(pedido);

        assertEquals(10, resposta.getId());
    }

    @Test
    void devePreservarMetadadosDePaginacao() {
        Pedido pedido = new Pedido();
        pedido.setId(10);
        PageRequest pageable = PageRequest.of(1, 5);
        Page<Pedido> pagina = new PageImpl<>(List.of(pedido), pageable, 12);

        Page<PedidoResponseDto> resposta = PedidoMapper.toDto(pagina);

        assertEquals(1, resposta.getNumber());
        assertEquals(5, resposta.getSize());
        assertEquals(12, resposta.getTotalElements());
        assertEquals(3, resposta.getTotalPages());
        assertEquals(10, resposta.getContent().get(0).getId());
    }
}
