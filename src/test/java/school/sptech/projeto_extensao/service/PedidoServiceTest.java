package school.sptech.projeto_extensao.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projeto_extensao.dto.PedidoRequestDto;
import school.sptech.projeto_extensao.model.*;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private HistoricoPedidoRepository historico;

    @Mock
    private PedidoRepository pedido;

    @InjectMocks
    private PedidoService service;

    @Test
    @DisplayName("Testar função listar se retorna vazio")
    void testarFuncaoListarSeRetornaVazio(){
        var listaDevolvida = Collections.EMPTY_LIST;
        Mockito.when(pedido.findAllByIsAtivoTrue()).thenReturn(listaDevolvida);

        List<Pedido> lista = service.listar();

        Assertions.assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Testar função listar se retorna dados")
    void testarFuncaoListarSeRetornaDados(){
        List<Pedido> listaTeste = new ArrayList<>();
        listaTeste.add(
                new Pedido(1,
                        "Teste Produto",
                        "Teste Produto",
                        5.50,
                        true,
                        false,
                        LocalDateTime.now().plusDays(3),
                        null,
                        new Entrega(),
                        new Pagamento(),
                        new Cliente(),
                        new Endereco()
                        )
        );

        Mockito.when(pedido.findAllByIsAtivoTrue()).thenReturn(listaTeste);

        List<Pedido> lista = service.listar();

        Assertions.assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Testar funcao ListarPorData se retorna vazio")
    void testarFuncaoListarPorDataSeRetornaVazio(){
        var listaDevolvida = Collections.EMPTY_LIST;
        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(Mockito.any(), Mockito.any())).thenReturn(listaDevolvida);

        List<Pedido> lista = service.listarPorData(LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        Assertions.assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Testar funcao ListarPorData se retorna pedidos dentro da data correta")
    void testarFuncaoListarPorDataSeRetornaPedidosDentroDaDataCorreta(){
        List<Pedido> listaTeste = new ArrayList<>();
        listaTeste.add(
                new Pedido(1,
                        "Teste Produto",
                        "Teste Produto",
                        5.50,
                        true,
                        false,
                        LocalDateTime.now().plusDays(3),
                        null,
                        new Entrega(),
                        new Pagamento(),
                        new Cliente(),
                        new Endereco()
                )
        );

        LocalDateTime dataInicio = LocalDateTime.now();

        LocalDateTime dataFim = LocalDateTime.now().plusDays(3);

        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim)).thenReturn(listaTeste);

        List<Pedido> lista = service.listarPorData(dataInicio, dataFim);

        Assertions.assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Testar funcao ListarPorData se não retorna pedidos fora da data correta")
    void testarFuncaoListarPorDataSeNaoRetornaPedidosForaDaDataCorreta(){
        List<Pedido> listaTeste = new ArrayList<>();
        listaTeste.add(
                new Pedido(1,
                        "Teste Produto",
                        "Teste Produto",
                        5.50,
                        true,
                        false,
                        LocalDateTime.now().plusDays(3),
                        null,
                        new Entrega(),
                        new Pagamento(),
                        new Cliente(),
                        new Endereco()
                )
        );

        listaTeste.add(
                new Pedido(2,
                        "Teste Produto 2",
                        "Teste Produto 2",
                        7.90,
                        true,
                        false,
                        LocalDateTime.now().plusDays(20),
                        null,
                        new Entrega(),
                        new Pagamento(),
                        new Cliente(),
                        new Endereco()
                )
        );

        listaTeste.add(
                new Pedido(3,
                        "Teste Produto 3",
                        "Teste Produto 3",
                        200.00,
                        true,
                        false,
                        LocalDateTime.now().plusDays(6),
                        null,
                        new Entrega(),
                        new Pagamento(),
                        new Cliente(),
                        new Endereco()
                )
        );

        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = LocalDateTime.now().plusDays(7);

        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim)).thenReturn(listaTeste.stream().filter( l ->
                l.getDataPedido().isAfter(dataInicio) &&
                        l.getDataPedido().isBefore(dataFim)).toList());

        List<Pedido> lista = service.listarPorData(dataInicio, dataFim);

        Assertions.assertEquals(2, lista.size());
    }
}