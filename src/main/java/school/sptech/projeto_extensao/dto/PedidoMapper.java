package school.sptech.projeto_extensao.dto;

import school.sptech.projeto_extensao.model.*;

import java.time.LocalDateTime;

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
                dto.getAtivo(),
                dto.getReagendado(),
                dto.getDataPedido(),
                dto.getDataModificacao(),
                dto.getDataCriacao(),
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
                dto.getAtivo(),
                dto.getDataPedido(),
                dto.getDataModificacao(),
                dto.getDataCriacao(),
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
                pedido.getAtivo(),
                pedido.getDataPedido(),
                pedido.getEntrega(),
                pedido.getPagamento(),
                pedido.getCliente(),
                pedido.getEndereco()
        );
    }

    public static HistoricoPedido toHistorico(Pedido pedido){
        if (pedido == null){
            return null;
        }
        return new HistoricoPedido(
                pedido,
                pedido.getCliente(),
                pedido.getEntrega(),
                pedido.getPagamento(),
                LocalDateTime.now()
        );
    }
}
