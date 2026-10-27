package school.sptech.projeto_extensao.service;

import com.google.api.services.calendar.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.projeto_extensao.Exception.ErroException;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.exception.StatusPedidoInvalidoException;
import school.sptech.projeto_extensao.mapper.PedidoMapper;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Endereco;
import school.sptech.projeto_extensao.model.Entrega;
import school.sptech.projeto_extensao.model.Pagamento;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.ClienteRepository;
import school.sptech.projeto_extensao.repository.EnderecoRepository;
import school.sptech.projeto_extensao.repository.EntregaRepository;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PagamentoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PedidoService {
    private final PedidoRepository service;
    private final HistoricoPedidoRepository historico;
    private final PedidoProducer producer;
    private final PagamentoRepository pagamentoRepository;
    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    public PedidoService(PedidoRepository service,
                         HistoricoPedidoRepository historico,
                         PedidoProducer producer,
                         PagamentoRepository pagamentoRepository,
                         EntregaRepository entregaRepository,
                         ClienteRepository clienteRepository,
                         EnderecoRepository enderecoRepository) {
        this.service = service;
        this.historico = historico;
        this.producer = producer;
        this.pagamentoRepository = pagamentoRepository;
        this.entregaRepository = entregaRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Page<Pedido> listar(Pageable pageable) {
        return service.findAllByIsAtivoTrue(pageable);
    }

    public Pedido encontrarPorId(Integer id) {
        return service.findById(id).orElse(null);
    }

    public Page<Pedido> listarPorData(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        return service.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim, pageable);
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

        if (statusTerminal(pedido.getEntrega()) || statusTerminal(pedido.getPagamento())) {
            throw new StatusPedidoInvalidoException("Pedido com status final não pode ser alterado.");
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

    public Pedido cadastrar(Pedido pedido, Event evento) {
        try {
            if (evento != null) {
                pedido.setEventoGoogleCalendarId(evento.getId());
            }

            validarEntidadesAtivas(pedido);
            return service.save(pedido);
        } catch (EntidadeNaoEncontradaException | StatusPedidoInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroException("Houve um erro durante a criação do Pedido.");
        }
    }

    private boolean statusTerminal(Entrega entrega) {
        return entrega != null && statusTerminal(entrega.getEstado());
    }

    private boolean statusTerminal(Pagamento pagamento) {
        return pagamento != null && statusTerminal(pagamento.getEstado());
    }

    private boolean statusTerminal(String estado) {
        return estado != null
                && (estado.equalsIgnoreCase("Entregue") || estado.equalsIgnoreCase("Cancelado"));
    }

    @Transactional
    public Pedido cadastrar(Pedido pedido) {
        try {
            validarEntidadesAtivas(pedido);
            Pedido salvo = service.save(pedido);
            producer.publicarCriacao(salvo);
            return salvo;
        } catch (EntidadeNaoEncontradaException | StatusPedidoInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroException("Houve um erro durante a criação do Pedido.");
        }
    }

    @Transactional
    public Pedido editar(Pedido pedido) {
        try {
            Pedido pedidoExistente = encontrarPorId(pedido.getId());
            if (pedidoExistente == null) {
                throw new EntidadeNaoEncontradaException("Entidade não encontrada.");
            }

            validarEntidadesAtivas(pedido);

            boolean dataPedidoAlterada = !Objects.equals(pedidoExistente.getDataPedido(), pedido.getDataPedido());
            boolean pedidoJaReagendado = Boolean.TRUE.equals(pedidoExistente.getIsReagendado());

            historico.save(PedidoMapper.toHistorico(pedidoExistente));

            pedidoExistente.setDataPedido(pedido.getDataPedido());
            pedidoExistente.setProduto(pedido.getProduto());
            pedidoExistente.setCliente(pedido.getCliente());
            pedidoExistente.setDescricao(pedido.getDescricao());
            pedidoExistente.setEndereco(pedido.getEndereco());
            pedidoExistente.setValor(pedido.getValor());
            pedidoExistente.setEventoGoogleCalendarId(pedido.getEventoGoogleCalendarId());

            pedidoExistente.setDataModificacao(LocalDateTime.now());
            pedidoExistente.setIsReagendado(pedidoJaReagendado || dataPedidoAlterada);

            Pedido salvo = service.save(pedidoExistente);
            producer.publicarAtualizacao(salvo);
            return salvo;
        } catch (EntidadeNaoEncontradaException | StatusPedidoInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroException("Houve um erro durante a edição do Pedido.");
        }
    }

    @Transactional
    public Integer deletar(Integer id) {
        try {
            Pedido pedido = encontrarPorId(id);
            if (pedido == null) {
                throw new EntidadeNaoEncontradaException("Entidade não encontrada.");
            }

            int resultado = service.desativarPedido(id);
            if (resultado > 0) {
                producer.publicarExclusao(pedido);
            }
            return resultado;
        } catch (EntidadeNaoEncontradaException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroException("Houve um erro durante a deleção do Pedido.");
        }
    }

    @Transactional
    public void vincularEventoGoogle(Integer pedidoId, String eventoGoogleCalendarId) {
        if (pedidoId == null || eventoGoogleCalendarId == null || eventoGoogleCalendarId.isBlank()) {
            return;
        }
        Pedido pedido = service.findById(pedidoId).get();
        if (pedido == null) {
            return;
        }
        if (eventoGoogleCalendarId.equals(pedido.getEventoGoogleCalendarId())) {
            return;
        }
        pedido.setEventoGoogleCalendarId(eventoGoogleCalendarId);
        service.save(pedido);
    }

    private void validarEntidadesAtivas(Pedido pedido) {
        if (pedido.getCliente() != null && pedido.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado."));

            if (!Boolean.TRUE.equals(cliente.getIsAtivo())) {
                throw new StatusPedidoInvalidoException("Cliente inativo não pode receber pedidos.");
            }

            pedido.setCliente(cliente);
        }

        if (pedido.getEndereco() != null && pedido.getEndereco().getId() != null) {
            Endereco endereco = enderecoRepository.findById(pedido.getEndereco().getId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado."));

            if (!Boolean.TRUE.equals(endereco.getIsAtivo())) {
                throw new StatusPedidoInvalidoException("Endereço inativo não pode receber pedidos.");
            }

            pedido.setEndereco(endereco);
        }
    }
}