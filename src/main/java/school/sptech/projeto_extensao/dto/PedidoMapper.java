package school.sptech.projeto_extensao.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import school.sptech.projeto_extensao.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoMapper {
    public static Pedido toEntity(Integer id, PedidoRequestDto dto){
        if (dto == null){
            return null;
        }
        return new Pedido(
                id,
                dto.getProduto(),
                dto.getDescricao(),
                dto.getValor(),
                dto.getIsAtivo(),
                dto.getIsReagendado(),
                dto.getDataPedido(),
                LocalDateTime.now(),
                dto.getEntrega(),
                dto.getPagamento(),
                dto.getCliente(),
                dto.getEndereco()
        );
    }

    public static Pedido toEntity(PedidoRequestDto dto){
        if (dto == null){
            return null;
        }
        return new Pedido(
                dto.getProduto(),
                dto.getDescricao(),
                dto.getValor(),
                dto.getIsAtivo(),
                dto.getDataPedido(),
                LocalDateTime.now(),
                dto.getEntrega(),
                dto.getPagamento(),
                dto.getCliente(),
                dto.getEndereco()
        );
    }

    public static PedidoResponseDto toDto(Pedido pedido){
        if (pedido == null){
            return null;
        }
        return new PedidoResponseDto(
                pedido.getProduto(),
                pedido.getDescricao(),
                pedido.getValor(),
                pedido.getIsAtivo(),
                pedido.getDataPedido(),
                pedido.getEntrega(),
                pedido.getPagamento(),
                pedido.getCliente(),
                pedido.getEndereco()
        );
    }

    public static List<PedidoResponseDto> toDto(List<Pedido> pedidos){
        return pedidos.stream().map(PedidoMapper::toDto).toList();
    }

    public static HistoricoPedido toHistorico(Pedido pedido){
        if (pedido == null){
            return null;
        }
        return new HistoricoPedido(
                null,
                pedido,
                pedido.getCliente(),
                pedido.getEntrega(),
                pedido.getPagamento(),
                LocalDateTime.now()
        );
    }
}