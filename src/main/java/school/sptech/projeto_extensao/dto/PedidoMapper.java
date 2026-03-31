package school.sptech.projeto_extensao.dto;

import school.sptech.projeto_extensao.model.Pedido;

public class PedidoMapper {
    public static Pedido toEntity(Integer id, PedidoRequestDto dto){
        return new Pedido(
                id,
                dto.getProduto(),
                dto.getDescricao(),
                dto.getValor(),
                dto.getDataModificacao(),
                dto.getDataPedido(),
                dto.getEntrega(),
                dto.getPagamento(),
                dto.getCliente(),
                dto.getEndereco()
        );
    }
}
