package school.sptech.projeto_extensao.service;

import com.google.api.services.calendar.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.projeto_extensao.Exception.ErroException;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.exception.StatusPedidoInvalidoException;
import school.sptech.projeto_extensao.mapper.PedidoMapper;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.EntregaRepository;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PagamentoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoService {
    private final PedidoRepository service;

    private final HistoricoPedidoRepository historico;

    private final GoogleCalendarService calendario;

    private final PagamentoRepository pagamentoRepository;

    private final EntregaRepository entregaRepository;

    public PedidoService(PedidoRepository service, HistoricoPedidoRepository historico, GoogleCalendarService calendario,
                         PagamentoRepository pagamentoRepository, EntregaRepository entregaRepository) {
        this.service = service;
        this.historico = historico;
        this.calendario = calendario;
        this.pagamentoRepository = pagamentoRepository;
        this.entregaRepository = entregaRepository;
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

    @Transactional
    public Pedido atualizarStatus(Integer id, Integer pagamentoId, Integer entregaId) {
        Pedido pedido = encontrarPorId(id);
        if (pedido == null) {
            throw new EntidadeNaoEncontradaException("Pedido não encontrado.");
        }

        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Status de pagamento não encontrado."));
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Status de entrega não encontrado."));

        if (statusCancelado(pagamento.getEstado()) || statusCancelado(entrega.getEstado())) {
            throw new StatusPedidoInvalidoException("Utilize o cancelamento do pedido para aplicar o status Cancelado.");
        }

        boolean pagamentoAlterado = pedido.getPagamento() == null
                || !Objects.equals(pedido.getPagamento().getId(), pagamentoId);
        boolean entregaAlterada = pedido.getEntrega() == null
                || !Objects.equals(pedido.getEntrega().getId(), entregaId);

        if (!pagamentoAlterado && !entregaAlterada) {
            return pedido;
        }

        historico.save(PedidoMapper.toHistorico(pedido));
        pedido.setPagamento(pagamento);
        pedido.setEntrega(entrega);
        pedido.setDataModificacao(LocalDateTime.now());
        return service.save(pedido);
    }

    private boolean statusCancelado(String estado) {
        return estado != null && estado.equalsIgnoreCase("Cancelado");
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

            boolean dataPedidoAlterada = !Objects.equals(
                    pedidoExistente.getDataPedido(),
                    pedido.getDataPedido()
            );
            boolean pedidoJaReagendado = Boolean.TRUE.equals(pedidoExistente.getIsReagendado());

            String eventoId = pedidoExistente.getEventoGoogleCalendarId();
            if (eventoId != null) calendario.atualizarEvento(eventoId, PedidoMapper.toGoogleApi(pedido));

            historico.save(PedidoMapper.toHistorico(pedidoExistente));

            pedidoExistente.setDataPedido(pedido.getDataPedido());
            pedidoExistente.setProduto(pedido.getProduto());
            pedidoExistente.setCliente(pedido.getCliente());
            pedidoExistente.setDescricao(pedido.getDescricao());
            pedidoExistente.setEndereco(pedido.getEndereco());
            pedidoExistente.setValor(pedido.getValor());

            pedidoExistente.setDataModificacao(LocalDateTime.now());
            pedidoExistente.setIsReagendado(pedidoJaReagendado || dataPedidoAlterada);
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
