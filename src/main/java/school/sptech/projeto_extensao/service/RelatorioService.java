package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.dto.*;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final PedidoRepository pedidoRepository;

    public RelatorioService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public RelatorioDto relatorioVendas(PeriodoFiltroDto periodoFiltro) {
        LocalDateTime inicioPeriodo = periodoFiltro.getDataInicio();
        LocalDateTime fimPeriodo = periodoFiltro.getDataFim();

        long duracaoEmSegundos = ChronoUnit.SECONDS.between(inicioPeriodo, fimPeriodo);
        LocalDateTime inicioPeriodoComparacao = inicioPeriodo.minusSeconds(duracaoEmSegundos);

        List<Pedido> pedidos = pedidoRepository.findAllByDataPedidoBetweenOrderByDataCriacaoDesc(inicioPeriodo, fimPeriodo);
        List<Pedido> qtdPedidosComparacao = pedidoRepository.findAllByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencaQtdPedidos = pedidos.size() - qtdPedidosComparacao.size();

        Integer canceladosPeriodo = pedidoRepository.countPedidosCanceladosByDataCriacaoBetween(inicioPeriodo, fimPeriodo);
        Integer canceladosComparacao = pedidoRepository.countPedidosCanceladosByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencaCancelados = canceladosPeriodo - canceladosComparacao;

        Integer reagendadasPeriodo = pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(inicioPeriodo, fimPeriodo);
        Integer reagendadasComparacao = pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencaReagendadas = reagendadasPeriodo - reagendadasComparacao;

        Integer clientesFidelizados = pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(inicioPeriodo, fimPeriodo);
        Integer clientesNovos = pedidoRepository.countClientesNovosNoPeriodo(inicioPeriodo, fimPeriodo);

        List<Pedido> listaPedidosPreMapper = pedidos.subList(0, Math.min(15, pedidos.size()));

        ArrayList<PedidoRelatorioDto> listaPedidos = new ArrayList<>();

        for (Pedido p : listaPedidosPreMapper) {
            listaPedidos.add(new PedidoRelatorioDto(p.getId(), p.getProduto(), p.getDataCriacao(), p.getEntrega(), p.getPagamento()));
        }

        return new RelatorioDto(pedidos.size(), diferencaQtdPedidos, canceladosPeriodo, diferencaCancelados,
                reagendadasPeriodo, diferencaReagendadas, clientesFidelizados, clientesNovos, listaPedidos);
    }

    public List<PedidosCanceladosDto> relatorioCancelados(PeriodoFiltroDto periodoFiltro) {
        LocalDateTime inicioPeriodo = periodoFiltro.getDataInicio();
        LocalDateTime fimPeriodo = periodoFiltro.getDataFim();

        List<Pedido> pedidosCanceladosPreMapper = pedidoRepository.findTop15CanceladosByDataCriacaoBetween(inicioPeriodo, fimPeriodo);
        ArrayList<PedidosCanceladosDto> pedidosCancelados = new ArrayList<>();

        for (Pedido p : pedidosCanceladosPreMapper) {
            pedidosCancelados.add(new PedidosCanceladosDto(p.getId(), p.getProduto(), p.getDataModificacao()));
        }

        return pedidosCancelados;
    }

    public List<PedidosReagendadosDto> relatorioReagendados(PeriodoFiltroDto periodoFiltro) {
        LocalDateTime inicioPeriodo = periodoFiltro.getDataInicio();
        LocalDateTime fimPeriodo = periodoFiltro.getDataFim();

        List<Pedido> pedidosReagendadosPreMapper = pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(inicioPeriodo, fimPeriodo);
        ArrayList<PedidosReagendadosDto> pedidosReagendados = new ArrayList<>();

        for (Pedido p : pedidosReagendadosPreMapper) {
            pedidosReagendados.add(new PedidosReagendadosDto(p.getId(), p.getProduto(), p.getEntrega(), p.getDataCriacao(),
                    p.getDataPedido(), p.getPagamento()));
        }

        return pedidosReagendados;
    }

    public List<ClienteDto> relatorioClientes(PeriodoFiltroDto periodoFiltro) {
        LocalDateTime inicioPeriodo = periodoFiltro.getDataInicio();
        LocalDateTime fimPeriodo = periodoFiltro.getDataFim();

        System.out.println("Inicio periodo: " + inicioPeriodo);
        System.out.println("Fim periodo: " + fimPeriodo);

        List<Cliente> clientesPreMapper = pedidoRepository.findTop15ClientesComPedidoNoPeriodo(inicioPeriodo, fimPeriodo);
        System.out.println("CLIENTES: " + clientesPreMapper);

        List<Cliente> clientesLimitados = clientesPreMapper.subList(0, Math.min(15, clientesPreMapper.size()));
        ArrayList<ClienteDto> clientes = new ArrayList<>();

        for (Cliente c : clientesLimitados) {
            clientes.add(new ClienteDto(c.getId(), c.getNome(), c.getTelefone(), c.getCpf()));
        }

        return clientes;
    }
}