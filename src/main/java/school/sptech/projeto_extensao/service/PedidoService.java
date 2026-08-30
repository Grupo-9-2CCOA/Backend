package school.sptech.projeto_extensao.service;

import com.google.api.services.calendar.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.Exception.ErroException;
import school.sptech.projeto_extensao.dto.pedido.PedidoResponseDto;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.mapper.PedidoMapper;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository service;

    private final HistoricoPedidoRepository historico;

    private final GoogleCalendarService calendario;

    public PedidoService(PedidoRepository service, HistoricoPedidoRepository historico, GoogleCalendarService calendario) {
        this.service = service;
        this.historico = historico;
        this.calendario = calendario;
    }

    public List<Pedido> listar(){
        return service.findAllByIsAtivoTrue();
    }

    public Pedido encontrarPorId(Integer id){
        return service.findByIdAndIsAtivoTrue(id);
    }

    public List<Pedido> listarPorData(LocalDateTime dataInicio, LocalDateTime dataFim){
        return service.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim);
    }

    public Pedido cadastrar(Pedido pedido, Event evento){
        try {
            pedido.setEventoGoogleCalendarId(evento.getId());

            return service.save(pedido);
        } catch (Exception e){
            throw new ErroException(
                    "Houve um erro durante a criação do Pedido."
            );
        }
    }

    public Pedido editar(Pedido pedido){
        try{
            Pedido pedidoExistente = encontrarPorId(pedido.getId());
            if (pedidoExistente == null) throw new EntidadeNaoEncontradaException(
                    "Entidade não encontrada."
            );

            String eventoId = pedidoExistente.getEventoGoogleCalendarId();
            if (eventoId != null) calendario.atualizarEvento(eventoId, PedidoMapper.toGoogleApi(pedido));

            historico.save(PedidoMapper.toHistorico(pedidoExistente));

            pedidoExistente.setDataPedido(pedido.getDataPedido());
            pedidoExistente.setProduto(pedido.getProduto());
            pedidoExistente.setCliente(pedido.getCliente());
            pedidoExistente.setDescricao(pedido.getDescricao());
            pedidoExistente.setEntrega(pedido.getEntrega());
            pedidoExistente.setEndereco(pedido.getEndereco());
            pedidoExistente.setPagamento(pedido.getPagamento());
            pedidoExistente.setIsAtivo(pedido.getIsAtivo());
            pedidoExistente.setValor(pedido.getValor());

            pedidoExistente.setDataModificacao(LocalDateTime.now());
            pedidoExistente.setIsReagendado(true);
            return service.save(pedidoExistente);
        } catch (Exception e){
            throw new ErroException(
                    "Houve um erro durante a edição do Pedido."
            );
        }
    }

    public Integer deletar(Integer id){
        try {
            Pedido pedido = encontrarPorId(id);
            if (pedido == null) throw new EntidadeNaoEncontradaException(
                    "Entidade não encontrada."
            );

            String eventoId = pedido.getEventoGoogleCalendarId();
            if (pedido.getEventoGoogleCalendarId() != null) calendario.deletarEvento(eventoId);

            return service.desativarPedido(id);
        } catch (Exception e){
            throw new ErroException(
                    "Houve um erro durante a deleção do Pedido."
            );
        }
    }
}
