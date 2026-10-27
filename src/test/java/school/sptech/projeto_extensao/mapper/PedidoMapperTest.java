package school.sptech.projeto_extensao.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PedidoMapperTest {

    @Test
    void deveAdicionarIdentificadorNaResposta() {
        Pedido pedido = new Pedido();
        pedido.setId(10);

        PedidoResponseDto resposta = PedidoMapper.toDto(pedido);

        assertEquals(10, resposta.getId());
    }

    @Test
    void devePreservarMetadadosDePaginacaoAoMapearPage() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        pedido1.setDataPedido(LocalDateTime.of(2025, 10, 13, 10, 0));

        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setDataPedido(LocalDateTime.of(2025, 10, 13, 11, 0));

        Page<Pedido> paginaOriginal = new PageImpl<>(
                List.of(pedido1, pedido2),
                PageRequest.of(1, 2),
                10
        );

        Page<PedidoResponseDto> paginaConvertida = PedidoMapper.toDto(paginaOriginal);

        assertEquals(10, paginaConvertida.getTotalElements());
        assertEquals(5, paginaConvertida.getTotalPages());
        assertEquals(1, paginaConvertida.getPageable().getPageNumber());
        assertEquals(2, paginaConvertida.getPageable().getPageSize());
        assertFalse(paginaConvertida.getPageable().isUnpaged());
        assertEquals(2, paginaConvertida.getContent().size());
        assertEquals(1, paginaConvertida.getContent().get(0).getId());
    }
}