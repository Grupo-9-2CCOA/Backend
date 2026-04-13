package school.sptech.projeto_extensao.dto;

import school.sptech.projeto_extensao.model.*;

public class PedidoMapper {
    public static Pedido toEntity(Integer id, PedidoRequestDto dto){
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
}
