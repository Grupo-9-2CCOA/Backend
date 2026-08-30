//package school.sptech.projeto_extensao.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import school.sptech.projeto_extensao.model.*;
//import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
//import school.sptech.projeto_extensao.repository.PedidoRepository;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class PedidoServiceTest {
//
//    @Mock
//    private HistoricoPedidoRepository historico;
//
//    @Mock
//    private PedidoRepository pedido;
//
//    @InjectMocks
//    private PedidoService service;
//
//    @Test
//    @DisplayName("Testar função listar se retorna vazio")
//    void testarFuncaoListarSeRetornaVazio(){
//        var listaDevolvida = Collections.EMPTY_LIST;
//        Mockito.when(pedido.findAllByIsAtivoTrue()).thenReturn(listaDevolvida);
//
//        List<Pedido> lista = service.listar();
//
//        Assertions.assertTrue(lista.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Testar função listar se retorna dados")
//    void testarFuncaoListarSeRetornaDados(){
//        List<Pedido> listaTeste = new ArrayList<>();
//        listaTeste.add(
//                new Pedido(1,
//                        "Teste Produto",
//                        "Teste Produto",
//                        5.50,
//                        true,
//                        false,
//                        LocalDateTime.now().plusDays(3),
//                        null,
//                        new Entrega(),
//                        new Pagamento(),
//                        new Cliente(),
//                        new Endereco()
//                        )
//        );
//
//        Mockito.when(pedido.findAllByIsAtivoTrue()).thenReturn(listaTeste);
//
//        List<Pedido> lista = service.listar();
//
//        Assertions.assertFalse(lista.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Testar funcao ListarPorData se retorna vazio")
//    void testarFuncaoListarPorDataSeRetornaVazio(){
//        var listaDevolvida = Collections.EMPTY_LIST;
//        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(Mockito.any(), Mockito.any())).thenReturn(listaDevolvida);
//
//        List<Pedido> lista = service.listarPorData(LocalDateTime.now(), LocalDateTime.now().plusDays(3));
//
//        Assertions.assertTrue(lista.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Testar funcao ListarPorData se retorna pedidos dentro da data correta")
//    void testarFuncaoListarPorDataSeRetornaPedidosDentroDaDataCorreta(){
//        List<Pedido> listaTeste = new ArrayList<>();
//        listaTeste.add(
//                new Pedido(1,
//                        "Teste Produto",
//                        "Teste Produto",
//                        5.50,
//                        true,
//                        false,
//                        LocalDateTime.now().plusDays(3),
//                        null,
//                        new Entrega(),
//                        new Pagamento(),
//                        new Cliente(),
//                        new Endereco()
//                )
//        );
//
//        LocalDateTime dataInicio = LocalDateTime.now();
//
//        LocalDateTime dataFim = LocalDateTime.now().plusDays(3);
//
//        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim)).thenReturn(listaTeste);
//
//        List<Pedido> lista = service.listarPorData(dataInicio, dataFim);
//
//        Assertions.assertFalse(lista.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Testar funcao ListarPorData se não retorna pedidos fora da data correta")
//    void testarFuncaoListarPorDataSeNaoRetornaPedidosForaDaDataCorreta(){
//        List<Pedido> listaTeste = new ArrayList<>();
//        listaTeste.add(
//                new Pedido(1,
//                        "Teste Produto",
//                        "Teste Produto",
//                        5.50,
//                        true,
//                        false,
//                        LocalDateTime.now().plusDays(3),
//                        null,
//                        new Entrega(),
//                        new Pagamento(),
//                        new Cliente(),
//                        new Endereco()
//                )
//        );
//
//        listaTeste.add(
//                new Pedido(2,
//                        "Teste Produto 2",
//                        "Teste Produto 2",
//                        7.90,
//                        true,
//                        false,
//                        LocalDateTime.now().plusDays(20),
//                        null,
//                        new Entrega(),
//                        new Pagamento(),
//                        new Cliente(),
//                        new Endereco()
//                )
//        );
//
//        listaTeste.add(
//                new Pedido(3,
//                        "Teste Produto 3",
//                        "Teste Produto 3",
//                        200.00,
//                        true,
//                        false,
//                        LocalDateTime.now().plusDays(6),
//                        null,
//                        new Entrega(),
//                        new Pagamento(),
//                        new Cliente(),
//                        new Endereco()
//                )
//        );
//
//        LocalDateTime dataInicio = LocalDateTime.now();
//        LocalDateTime dataFim = LocalDateTime.now().plusDays(7);
//
//        Mockito.when(pedido.findAllByIsAtivoTrueAndDataPedidoBetween(dataInicio, dataFim)).thenReturn(listaTeste.stream().filter( l ->
//                l.getDataPedido().isAfter(dataInicio) &&
//                        l.getDataPedido().isBefore(dataFim)).toList());
//
//        List<Pedido> lista = service.listarPorData(dataInicio, dataFim);
//
//        Assertions.assertEquals(2, lista.size());
//    }
//
//    @Test
//    @DisplayName("Testar função encontrarPorId se retorna pedido")
//    void testarFuncaoEncontrarPorIdSeRetornaPedido() {
//
//        Pedido pedidoTeste = new Pedido(
//                1,
//                "Teste Produto",
//                "Teste Produto",
//                5.50,
//                true,
//                false,
//                LocalDateTime.now().plusDays(3),
//                null,
//                new Entrega(),
//                new Pagamento(),
//                new Cliente(),
//                new Endereco()
//        );
//
//        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoTeste);
//
//        Pedido retorno = service.encontrarPorId(1);
//
//        Assertions.assertNotNull(retorno);
//        Assertions.assertEquals(1, retorno.getId());
//        Assertions.assertEquals("Teste Produto", retorno.getProduto());
//    }
//
//    @Test
//    @DisplayName("Testar função encontrarPorId se retorna null")
//    void testarFuncaoEncontrarPorIdSeRetornaNull() {
//
//        Mockito.when(pedido.findByIdAndIsAtivoTrue(999)).thenReturn(null);
//
//        Pedido retorno = service.encontrarPorId(999);
//
//        Assertions.assertNull(retorno);
//    }
//
//    @Test
//    @DisplayName("Testar função cadastrar se salva pedido corretamente")
//    void testarFuncaoCadastrarSeSalvaPedidoCorretamente() {
//
//        Pedido pedidoTeste = new Pedido(
//                1,
//                "Produto Cadastro",
//                "Descricao Cadastro",
//                100.0,
//                true,
//                false,
//                LocalDateTime.now().plusDays(5),
//                null,
//                new Entrega(),
//                new Pagamento(),
//                new Cliente(),
//                new Endereco()
//        );
//
//        Mockito.when(pedido.save(Mockito.any(Pedido.class))).thenReturn(pedidoTeste);
//
//        Pedido retorno = service.cadastrar(pedidoTeste);
//
//        Assertions.assertNotNull(retorno);
//        Assertions.assertEquals("Produto Cadastro", retorno.getProduto());
//
//        Mockito.verify(pedido, Mockito.times(1))
//                .save(Mockito.any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Testar função editar se altera data de modificação")
//    void testarFuncaoEditarSeAlteraDataDeModificacao() {
//
//        Pedido pedidoTeste = new Pedido(
//                1,
//                "Produto Editado",
//                "Descricao Editada",
//                200.0,
//                true,
//                false,
//                LocalDateTime.now().plusDays(2),
//                null,
//                new Entrega(),
//                new Pagamento(),
//                new Cliente(),
//                new Endereco()
//        );
//
//        Mockito.when(pedido.save(Mockito.any(Pedido.class))).thenReturn(pedidoTeste);
//
//        Pedido retorno = service.editar(pedidoTeste);
//
//        Assertions.assertNotNull(retorno);
//        Assertions.assertNotNull(pedidoTeste.getDataModificacao());
//
//        Mockito.verify(historico, Mockito.times(1))
//                .save(Mockito.any());
//
//        Mockito.verify(pedido, Mockito.times(1))
//                .save(Mockito.any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Testar função deletar se desativa pedido existente")
//    void testarFuncaoDeletarSeDesativaPedidoExistente() {
//
//        Pedido pedidoTeste = new Pedido(
//                1,
//                "Produto",
//                "Descricao",
//                50.0,
//                true,
//                false,
//                LocalDateTime.now(),
//                null,
//                new Entrega(),
//                new Pagamento(),
//                new Cliente(),
//                new Endereco()
//        );
//
//        Mockito.when(pedido.findByIdAndIsAtivoTrue(1))
//                .thenReturn(pedidoTeste);
//
//        Mockito.when(pedido.desativarPedido(1))
//                .thenReturn(1);
//
//        Integer retorno = service.deletar(1);
//
//        Assertions.assertEquals(1, retorno);
//
//        Mockito.verify(pedido, Mockito.times(1))
//                .desativarPedido(1);
//    }
//
//    @Test
//    @DisplayName("Testar função deletar se retorna 0 para pedido inexistente")
//    void testarFuncaoDeletarSeRetornaZeroParaPedidoInexistente() {
//
//        Mockito.when(pedido.findByIdAndIsAtivoTrue(999))
//                .thenReturn(null);
//
//        Integer retorno = service.deletar(999);
//
//        Assertions.assertEquals(0, retorno);
//
//        Mockito.verify(pedido, Mockito.never())
//                .desativarPedido(Mockito.anyInt());
//    }
//
//    @Test
//    @DisplayName("Testar função editar se salva histórico corretamente")
//    void testarFuncaoEditarSeSalvaHistoricoCorretamente() {
//
//        Pedido pedidoTeste = new Pedido(
//                1,
//                "Produto Histórico",
//                "Descricao Histórico",
//                75.0,
//                true,
//                false,
//                LocalDateTime.now().plusDays(1),
//                null,
//                new Entrega(),
//                new Pagamento(),
//                new Cliente(),
//                new Endereco()
//        );
//
//        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
//                .thenReturn(pedidoTeste);
//
//        service.editar(pedidoTeste);
//
//        Mockito.verify(historico, Mockito.times(1))
//                .save(Mockito.any());
//
//        Mockito.verify(pedido, Mockito.times(1))
//                .save(Mockito.any(Pedido.class));
//    }
//
//    @Test
//    @DisplayName("Testar se editar atualiza dataModificacao")
//    void testarSeEditarAtualizaDataModificacao() {
//
//        LocalDateTime dataAntiga = LocalDateTime.of(2025, 1, 1, 10, 0);
//
//        Pedido pedidoTeste = new Pedido();
//
//        pedidoTeste.setDataModificacao(dataAntiga);
//
//        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
//                .thenReturn(pedidoTeste);
//
//        service.editar(pedidoTeste);
//
//        Assertions.assertNotEquals(
//                dataAntiga,
//                pedidoTeste.getDataModificacao()
//        );
//    }
//
//    @Test
//    @DisplayName("Testar se editar envia pedido correto para save")
//    void testarSeEditarEnviaPedidoCorretoParaSave() {
//
//        Pedido pedidoTeste = new Pedido();
//        pedidoTeste.setProduto("Produto Teste");
//
//        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
//                .thenReturn(pedidoTeste);
//
//        service.editar(pedidoTeste);
//
//        Mockito.verify(pedido)
//                .save(pedidoTeste);
//    }
//}