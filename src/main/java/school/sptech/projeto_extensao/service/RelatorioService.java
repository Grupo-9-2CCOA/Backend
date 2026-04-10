package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.controller.enums.PeriodoFiltro;
import school.sptech.projeto_extensao.dto.*;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Pedido;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final PedidoRepository pedidoRepository;

    public RelatorioService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public RelatorioDto relatorioVendas(PeriodoFiltro periodoFiltro) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo;
        LocalDateTime inicioPeriodoComparacao;

        switch (periodoFiltro) {
            case SEMANA:
                inicioPeriodo = agora.minusDays(7).toLocalDate().atStartOfDay();
                inicioPeriodoComparacao = inicioPeriodo.minusDays(7);
                break;
            case MENSAL:
                inicioPeriodo = agora.minusMonths(1).toLocalDate().atStartOfDay();
                inicioPeriodoComparacao = inicioPeriodo.minusMonths(1);
                break;
            case SEMESTRAL:
                inicioPeriodo = agora.minusMonths(6).toLocalDate().atStartOfDay();
                inicioPeriodoComparacao = inicioPeriodo.minusMonths(6);
                break;
            case ANUAL:
                inicioPeriodo = agora.minusYears(1).toLocalDate().atStartOfDay();
                inicioPeriodoComparacao = inicioPeriodo.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Período inválido: " + periodoFiltro);
        }


        List<Pedido> pedidos = pedidoRepository.findAllByDataPedidoBetweenOrderByDataCriacaoDesc(inicioPeriodo, agora);
        List<Pedido> qtdPedidosComparacao = pedidoRepository.findAllByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencaQtdPedidos = pedidos.size() - qtdPedidosComparacao.size();

        Integer canceladosPeriodo = pedidoRepository.countPedidosCanceladosByDataCriacaoBetween(inicioPeriodo, agora);
        Integer canceladosComparacao = pedidoRepository.countPedidosCanceladosByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencacancelados = canceladosPeriodo - canceladosComparacao;

        Integer reagendadasPeriodo = pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(inicioPeriodo, agora);
        Integer reagendadasComparacao = pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(inicioPeriodoComparacao, inicioPeriodo);
        Integer diferencaReagendadas = reagendadasPeriodo - reagendadasComparacao;

        Integer clientesFidelizados = pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(inicioPeriodo, agora);
        Integer clientesNovos = pedidoRepository.countClientesNovosNoPeriodo(inicioPeriodo, agora);

        List<Pedido> listaPedidosPreMapper = pedidos.subList(0, Math.min(15, pedidos.size()));

        ArrayList<PedidoRelatorioDto> listaPedidos = new ArrayList<>();

        for(Pedido p : listaPedidosPreMapper){
            listaPedidos.add(new PedidoRelatorioDto(p.getId(), p.getProduto(), p.getDataCriacao(), p.getEntrega(), p.getPagamento()));
        }

        return new RelatorioDto(pedidos.size(), diferencaQtdPedidos, canceladosPeriodo, diferencacancelados, reagendadasPeriodo,
                diferencaReagendadas, clientesFidelizados, clientesNovos, listaPedidos);
    }

    public List<PedidosCanceladosDto> relatorioCancelados(PeriodoFiltro periodoFiltro){
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo = null;
        switch (periodoFiltro) {
            case SEMANA:
                inicioPeriodo = agora.minusDays(7).toLocalDate().atStartOfDay();
                break;
            case MENSAL:
                inicioPeriodo = agora.minusMonths(1).toLocalDate().atStartOfDay();
                break;
            case SEMESTRAL:
                inicioPeriodo = agora.minusMonths(6).toLocalDate().atStartOfDay();
                break;
            case ANUAL:
                inicioPeriodo = agora.minusYears(1).toLocalDate().atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("Período inválido: " + periodoFiltro);
        }

        List<Pedido> pedidosCanceladosPreMapper = pedidoRepository.findTop15CanceladosByDataCriacaoBetween(inicioPeriodo, agora);
        ArrayList<PedidosCanceladosDto> pedidosCancelados = new ArrayList<>();

        for(Pedido p : pedidosCanceladosPreMapper){
            pedidosCancelados.add(new PedidosCanceladosDto(p.getId(), p.getProduto(), p.getDataModificacao()));
        }
        
        return pedidosCancelados;
    }

    public List<PedidosReagendadosDto> relatorioReagendados(PeriodoFiltro periodoFiltro) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo;

        switch (periodoFiltro) {
            case SEMANA:
                inicioPeriodo = agora.minusDays(7).toLocalDate().atStartOfDay();
                break;
            case MENSAL:
                inicioPeriodo = agora.minusMonths(1).toLocalDate().atStartOfDay();
                break;
            case SEMESTRAL:
                inicioPeriodo = agora.minusMonths(6).toLocalDate().atStartOfDay();
                break;
            case ANUAL:
                inicioPeriodo = agora.minusYears(1).toLocalDate().atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("Período inválido: " + periodoFiltro);
        }

        List<Pedido> pedidosReagendadosPreMapper = pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(inicioPeriodo, agora);
        ArrayList<PedidosReagendadosDto> pedidosReagendados = new ArrayList<>();

        for (Pedido p : pedidosReagendadosPreMapper) {
            pedidosReagendados.add(new PedidosReagendadosDto(p.getId(), p.getProduto(), p.getEntrega(), p.getDataCriacao(),
                    p.getDataPedido(), p.getPagamento()));
        }

        return pedidosReagendados;
    }

    public List<ClienteDto> relatorioClientes(PeriodoFiltro periodoFiltro) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodo;

        switch (periodoFiltro) {
            case SEMANA:
                inicioPeriodo = agora.minusDays(7).toLocalDate().atStartOfDay();
                break;
            case MENSAL:
                inicioPeriodo = agora.minusMonths(1).toLocalDate().atStartOfDay();
                break;
            case SEMESTRAL:
                inicioPeriodo = agora.minusMonths(6).toLocalDate().atStartOfDay();
                break;
            case ANUAL:
                inicioPeriodo = agora.minusYears(1).toLocalDate().atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("Período inválido: " + periodoFiltro);
        }

        System.out.println("Inicio periodo: " + inicioPeriodo);
        System.out.println("Inicio periodo: " + agora);

        List<Cliente> clientesPreMapper = pedidoRepository.findTop15ClientesComPedidoNoPeriodo(inicioPeriodo, agora);
        System.out.println("CLIENTES: " + clientesPreMapper);
        List<Cliente> clientesLimitados = clientesPreMapper.subList(0, Math.min(15, clientesPreMapper.size()));

        ArrayList<ClienteDto> clientes = new ArrayList<>();

        for (Cliente c : clientesLimitados) {
            clientes.add(new ClienteDto(c.getId(), c.getNome(), c.getTelefone(), c.getCpf()));
        }

        return clientes;
    }

}
