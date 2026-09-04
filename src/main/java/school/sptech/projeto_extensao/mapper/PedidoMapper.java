package school.sptech.projeto_extensao.mapper;

import school.sptech.projeto_extensao.dto.EventoCalendarDto;
import school.sptech.projeto_extensao.dto.pedido.PedidoRequestDto;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
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
                dto.getEndereco(),
                dto.getEventoGoogleCalendarId()
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
                dto.getEndereco(),
                dto.getEventoGoogleCalendarId()
        );
    }

    public static PedidoResponseDto toDto(Pedido pedido){
        if (pedido == null){
            return null;
        }
        return new PedidoResponseDto(
                pedido.getId(),
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

    public static EventoCalendarDto toGoogleApi(Pedido pedido){
        if (pedido == null){
            return null;
        }
        return new EventoCalendarDto(
                pedido.getProduto(),
                pedido.getDescricao(),
                pedido.getDataPedido(),
                pedido.getDataPedido().plusHours(1)
        );
    }
}
