package school.sptech.projeto_extensao.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projeto_extensao.dto.*;
import school.sptech.projeto_extensao.dto.cliente.ClienteDto;
import school.sptech.projeto_extensao.dto.pedido.PedidosCanceladosDto;
import school.sptech.projeto_extensao.dto.pedido.PedidosReagendadosDto;
import school.sptech.projeto_extensao.dto.pedido.PeriodoFiltroDto;
import school.sptech.projeto_extensao.model.*;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService relatorioService;

    @Mock
    private PedidoRepository pedidoRepository;


    private PeriodoFiltroDto periodoSemana() {
        return new PeriodoFiltroDto(OffsetDateTime.now().minusDays(7), OffsetDateTime.now());
    }

    private PeriodoFiltroDto periodoMensal() {
        return new PeriodoFiltroDto(OffsetDateTime.now().minusMonths(1), OffsetDateTime.now());
    }

    private PeriodoFiltroDto periodoSemestral() {
        return new PeriodoFiltroDto(OffsetDateTime.now().minusMonths(6), OffsetDateTime.now());
    }

    private PeriodoFiltroDto periodoAnual() {
        return new PeriodoFiltroDto(OffsetDateTime.now().minusYears(1), OffsetDateTime.now());
    }


    @Test
    void deveRetornarRelatorioVendasComDadosPeriodoSemana() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        pedidosPeriodo.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        pedidosPeriodo.add(new Pedido(2, "Bolo 2", "Descrição 2", 60.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        ArrayList<Pedido> pedidosComparacao = new ArrayList<>();
        pedidosComparacao.add(new Pedido(3, "Bolo 3", "Descrição 3", 40.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosComparacao);
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(2);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoSemana());

        assertNotNull(relatorio);
        assertEquals(2, relatorio.getQtdPedidos());
        assertEquals(1, relatorio.getDiferencaQtdPedidos());
        assertEquals(2, relatorio.getClientesFidelizados());
        assertEquals(1, relatorio.getClientesNovos());
    }

    @Test
    void deveRetornarRelatorioVendasPeriodoMensal() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        pedidosPeriodo.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(1);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoMensal());

        assertNotNull(relatorio);
        assertEquals(1, relatorio.getQtdPedidos());
    }

    @Test
    void deveRetornarRelatorioVendasPeriodoSemestral() {
        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(0);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoSemestral());

        assertNotNull(relatorio);
        assertEquals(0, relatorio.getQtdPedidos());
    }

    @Test
    void deveRetornarRelatorioVendasPeriodoAnual() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            pedidosPeriodo.add(new Pedido(i, "Bolo " + i, "Descrição " + i, 50.0 + i, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(5).thenReturn(2);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(3).thenReturn(1);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(10);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(5);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoAnual());

        assertNotNull(relatorio);
        assertEquals(20, relatorio.getQtdPedidos());
        assertEquals(3, relatorio.getDiferencaCanceladas());
        assertEquals(2, relatorio.getDiferencaReagendadas());
        assertTrue(relatorio.getPedidos().size() <= 15);
    }

    @Test
    void deveRetornarListaPedidosLimitadaA15NoRelatorioVendas() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            pedidosPeriodo.add(new Pedido(i, "Bolo " + i, "Descrição " + i, 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(0);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoSemana());

        assertEquals(15, relatorio.getPedidos().size());
    }


    @Test
    void deveRetornarListaPedidosCanceladosPeriodoSemana() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        pedidosCancelados.add(new Pedido(1, "Bolo Cancelado", "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        pedidosCancelados.add(new Pedido(2, "Bolo 2 Cancelado", "Descrição 2", 60.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoSemana());

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPedidosCancelados() {
        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoMensal());

        assertNotNull(pedidos);
        assertEquals(0, pedidos.size());
    }

    @Test
    void deveRetornarPedidosCanceladosPeriodoMensal() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        pedidosCancelados.add(new Pedido(1, "Bolo", "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoMensal());

        assertEquals(1, pedidos.size());
    }

    @Test
    void deveRetornarPedidosCanceladosPeriodoSemestral() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pedidosCancelados.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoSemestral());

        assertEquals(10, pedidos.size());
    }

    @Test
    void deveRetornarPedidosCanceladosPeriodoAnual() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            pedidosCancelados.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoAnual());

        assertEquals(15, pedidos.size());
    }


    @Test
    void deveRetornarListaPedidosReagendadosPeriodoSemana() {
        ArrayList<Pedido> pedidosReagendados = new ArrayList<>();
        pedidosReagendados.add(new Pedido(1, "Bolo Reagendado", "Descrição", 50.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        pedidosReagendados.add(new Pedido(2, "Bolo 2 Reagendado", "Descrição 2", 60.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosReagendados);

        List<PedidosReagendadosDto> pedidos = relatorioService.relatorioReagendados(periodoSemana());

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPedidosReagendados() {
        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        List<PedidosReagendadosDto> pedidos = relatorioService.relatorioReagendados(periodoMensal());

        assertNotNull(pedidos);
        assertEquals(0, pedidos.size());
    }

    @Test
    void deveRetornarPedidosReagendadosPeriodoMensal() {
        ArrayList<Pedido> pedidosReagendados = new ArrayList<>();
        pedidosReagendados.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosReagendados);

        List<PedidosReagendadosDto> pedidos = relatorioService.relatorioReagendados(periodoMensal());

        assertEquals(1, pedidos.size());
    }

    @Test
    void deveRetornarPedidosReagendadosPeriodoSemestral() {
        ArrayList<Pedido> pedidosReagendados = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pedidosReagendados.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosReagendados);

        List<PedidosReagendadosDto> pedidos = relatorioService.relatorioReagendados(periodoSemestral());

        assertEquals(8, pedidos.size());
    }

    @Test
    void deveRetornarPedidosReagendadosPeriodoAnual() {
        ArrayList<Pedido> pedidosReagendados = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            pedidosReagendados.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosReagendados);

        List<PedidosReagendadosDto> pedidos = relatorioService.relatorioReagendados(periodoAnual());

        assertEquals(12, pedidos.size());
    }


    @Test
    void deveRetornarListaClientesComPedidoPeriodoSemana() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "João Silva", "12345678901", "11999999999", true));
        clientes.add(new Cliente(2, "Maria Santos", "12345678902", "11888888888", true));

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoSemana());

        assertNotNull(clientesRetorno);
        assertEquals(2, clientesRetorno.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientesComPedido() {
        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        List<ClienteDto> clientes = relatorioService.relatorioClientes(periodoMensal());

        assertNotNull(clientes);
        assertEquals(0, clientes.size());
    }

    @Test
    void deveRetornarClientesPeriodoMensal() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "João", "12345678901", "11999999999", true));

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoMensal());

        assertEquals(1, clientesRetorno.size());
    }

    @Test
    void deveRetornarClientesPeriodoSemestral() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clientes.add(new Cliente(i, "Cliente " + i, "1234567890" + i, "1199999999" + i, true));
        }

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoSemestral());

        assertEquals(10, clientesRetorno.size());
    }

    @Test
    void deveRetornarClientesPeriodoAnual() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            clientes.add(new Cliente(i, "Cliente " + i, "1234567890" + i, "1199999999" + i, true));
        }

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoAnual());

        assertEquals(15, clientesRetorno.size());
    }

    @Test
    void deveRetornarClientesLimitadoA15() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            clientes.add(new Cliente(i, "Cliente " + i, "1234567890" + i, "1199999999" + i, true));
        }

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoAnual());

        assertEquals(15, clientesRetorno.size());
    }


    @Test
    void deveVerificarChamadasDoRepositorioNoRelatorioVendas() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        pedidosPeriodo.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(1);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        relatorioService.relatorioVendas(periodoSemana());

        verify(pedidoRepository, times(1)).findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(pedidoRepository, times(1)).findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(pedidoRepository, times(2)).countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(pedidoRepository, times(2)).countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(pedidoRepository, times(1)).countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(2));
        verify(pedidoRepository, times(1)).countClientesNaoFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(2));
        verify(pedidoRepository, times(1)).countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void deveVerificarChamadasDoRepositorioCancelados() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        pedidosCancelados.add(new Pedido(1, "Bolo", "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        relatorioService.relatorioCancelados(periodoSemana());

        verify(pedidoRepository, times(1)).findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void deveVerificarChamadasDoRepositorioReagendados() {
        ArrayList<Pedido> pedidosReagendados = new ArrayList<>();
        pedidosReagendados.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosReagendados);

        relatorioService.relatorioReagendados(periodoSemana());

        verify(pedidoRepository, times(1)).findTop15ReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void deveVerificarChamadasDoRepositorioClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "João", "12345678901", "11999999999", true));

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        relatorioService.relatorioClientes(periodoSemana());

        verify(pedidoRepository, times(1)).findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class));
    }



    @Test
    void deveValidarQueDadosNaoSaoNulosNoRelatorioVendas() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        pedidosPeriodo.add(new Pedido(1, "Bolo", "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(1);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoSemana());

        assertNotNull(relatorio);
        assertNotNull(relatorio.getPedidos());
        assertNotNull(relatorio.getQtdPedidos());
        assertNotNull(relatorio.getDiferencaQtdPedidos());
        assertTrue(relatorio.getQtdPedidos() >= 0);
    }

    @Test
    void deveValidarQuePedidosNaoSaoNulosNoCancelados() {
        ArrayList<Pedido> pedidosCancelados = new ArrayList<>();
        pedidosCancelados.add(new Pedido(1, "Bolo", "Descrição", 50.0, false, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosCancelados);

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodoSemana());

        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        pedidos.forEach(p -> assertNotNull(p.getId()));
    }

    @Test
    void deveValidarQueClientesNaoSaoNulosNoRelatorio() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1, "João", "12345678901", "11999999999", true));

        when(pedidoRepository.findTop15ClientesComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(clientes);

        List<ClienteDto> clientesRetorno = relatorioService.relatorioClientes(periodoSemana());

        assertNotNull(clientesRetorno);
        assertFalse(clientesRetorno.isEmpty());
        clientesRetorno.forEach(c -> {
            assertNotNull(c.getId());
            assertNotNull(c.getNome());
        });
    }

    @Test
    void deveUsarMinimoPadraoDeDoisPedidosNoPeriodoFiltro() {
        PeriodoFiltroDto periodo = new PeriodoFiltroDto(
                OffsetDateTime.parse("2026-01-01T00:00:00-03:00"),
                OffsetDateTime.parse("2026-01-31T23:59:59-03:00"));

        assertEquals(2, periodo.getMinimoPedidosFidelizacao());
    }

    @Test
    void deveRepassarMinimoCustomizadoESepararClientesFidelizadosENaoFidelizados() {
        PeriodoFiltroDto periodo = new PeriodoFiltroDto(
                OffsetDateTime.parse("2026-02-01T00:00:00-03:00"),
                OffsetDateTime.parse("2026-02-11T00:00:00-03:00"),
                4);
        LocalDateTime inicio = periodo.getDataInicio();
        LocalDateTime fim = periodo.getDataFim();
        LocalDateTime inicioComparacao = inicio.minusDays(10);
        LocalDateTime fimComparacao = inicio.minusNanos(1);
        LocalDateTime dataCriacao = LocalDateTime.of(2026, 2, 10, 15, 30);
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setProduto("Bolo");
        pedido.setDataCriacao(dataCriacao);

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(inicio, fim))
                .thenReturn(List.of(pedido));
        when(pedidoRepository.findAllByDataCriacaoBetween(inicioComparacao, fimComparacao))
                .thenReturn(Collections.emptyList());
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(inicio, fim))
                .thenReturn(3);
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(inicioComparacao, fimComparacao))
                .thenReturn(1);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(inicio, fim, 4))
                .thenReturn(7);
        when(pedidoRepository.countClientesNaoFidelizadosComPedidoNoPeriodo(inicio, fim, 4))
                .thenReturn(5);
        when(pedidoRepository.countClientesNovosNoPeriodo(inicio, fim))
                .thenReturn(1);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodo);

        assertEquals(7, relatorio.getClientesFidelizados());
        assertEquals(5, relatorio.getClientesNaoFidelizados());
        assertEquals(3, relatorio.getPedidosCancelados());
        assertEquals(2, relatorio.getDiferencaCanceladas());
        assertEquals(dataCriacao, relatorio.getPedidos().get(0).getDataCriacao());
        verify(pedidoRepository).findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(inicio, fim);
        verify(pedidoRepository).countPedidosCanceladosByDataModificacaoBetween(inicio, fim);
        verify(pedidoRepository).findAllByDataCriacaoBetween(inicioComparacao, fimComparacao);
        verify(pedidoRepository).countPedidosCanceladosByDataModificacaoBetween(inicioComparacao, fimComparacao);
        verify(pedidoRepository).countPedidosReagendadosByDataCriacaoBetween(inicioComparacao, fimComparacao);
        verify(pedidoRepository).countClientesFidelizadosComPedidoNoPeriodo(inicio, fim, 4);
        verify(pedidoRepository).countClientesNaoFidelizadosComPedidoNoPeriodo(inicio, fim, 4);
    }

    @Test
    void deveBuscarEMapearCanceladosPelaDataModificacao() {
        PeriodoFiltroDto periodo = new PeriodoFiltroDto(
                OffsetDateTime.parse("2026-03-01T00:00:00-03:00"),
                OffsetDateTime.parse("2026-03-31T23:59:59-03:00"));
        LocalDateTime dataModificacao = LocalDateTime.of(2026, 3, 15, 12, 0);
        Pedido pedidoCancelado = new Pedido();
        pedidoCancelado.setId(9);
        pedidoCancelado.setProduto("Torta");
        pedidoCancelado.setDataModificacao(dataModificacao);

        when(pedidoRepository.findTop15CanceladosByDataModificacaoBetween(
                periodo.getDataInicio(), periodo.getDataFim()))
                .thenReturn(List.of(pedidoCancelado));

        List<PedidosCanceladosDto> pedidos = relatorioService.relatorioCancelados(periodo);

        assertEquals(1, pedidos.size());
        assertEquals(dataModificacao, pedidos.get(0).getDataCancelamento());
        verify(pedidoRepository).findTop15CanceladosByDataModificacaoBetween(
                periodo.getDataInicio(), periodo.getDataFim());
    }

    @Test
    void deveRetornarDiferencaCorretaNoRelatorioVendas() {
        ArrayList<Pedido> pedidosPeriodo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pedidosPeriodo.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        ArrayList<Pedido> pedidosComparacao = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pedidosComparacao.add(new Pedido(i, "Bolo " + i, "Descrição", 50.0, true, false, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null));
        }

        when(pedidoRepository.findAllByDataCriacaoBetweenOrderByDataCriacaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosPeriodo);
        when(pedidoRepository.findAllByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(pedidosComparacao);
        when(pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(2).thenReturn(1);
        when(pedidoRepository.countPedidosReagendadosByDataCriacaoBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1).thenReturn(0);
        when(pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(3);
        when(pedidoRepository.countClientesNovosNoPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1);

        RelatorioDto relatorio = relatorioService.relatorioVendas(periodoSemana());

        assertEquals(5, relatorio.getQtdPedidos());
        assertEquals(2, relatorio.getDiferencaQtdPedidos());
        assertEquals(1, relatorio.getDiferencaCanceladas());
        assertEquals(1, relatorio.getDiferencaReagendadas());
    }
}
