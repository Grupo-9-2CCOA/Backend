package school.sptech.projeto_extensao.service;

import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projeto_extensao.model.*;
import school.sptech.projeto_extensao.exception.EntidadeNaoEncontradaException;
import school.sptech.projeto_extensao.exception.StatusPedidoInvalidoException;
import school.sptech.projeto_extensao.repository.EntregaRepository;
import school.sptech.projeto_extensao.repository.HistoricoPedidoRepository;
import school.sptech.projeto_extensao.repository.PagamentoRepository;
import school.sptech.projeto_extensao.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private HistoricoPedidoRepository historico;

    @Mock
    private PedidoRepository pedido;

    @Mock
    private GoogleCalendarService calendario;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private EntregaRepository entregaRepository;

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
                        new Endereco(),
                        null
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
                        new Endereco(),
                        null
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
                        new Endereco(),
                        null
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
                        new Endereco(),
                        null
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
                        new Endereco(),
                        null
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

    @Test
    @DisplayName("Testar função encontrarPorId se retorna pedido")
    void testarFuncaoEncontrarPorIdSeRetornaPedido() {

        Pedido pedidoTeste = new Pedido(
                1,
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
                new Endereco(),
                null
        );

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoTeste);

        Pedido retorno = service.encontrarPorId(1);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.getId());
        Assertions.assertEquals("Teste Produto", retorno.getProduto());
    }

    @Test
    @DisplayName("Testar função encontrarPorId se retorna null")
    void testarFuncaoEncontrarPorIdSeRetornaNull() {

        Mockito.when(pedido.findByIdAndIsAtivoTrue(999)).thenReturn(null);

        Pedido retorno = service.encontrarPorId(999);

        Assertions.assertNull(retorno);
    }

    @Test
    @DisplayName("Testar função cadastrar se salva pedido corretamente")
    void testarFuncaoCadastrarSeSalvaPedidoCorretamente() {

        Pedido pedidoTeste = new Pedido(
                1,
                "Produto Cadastro",
                "Descricao Cadastro",
                100.0,
                true,
                false,
                LocalDateTime.now().plusDays(5),
                null,
                new Entrega(),
                new Pagamento(),
                new Cliente(),
                new Endereco(),
                null
        );

        Mockito.when(pedido.save(Mockito.any(Pedido.class))).thenReturn(pedidoTeste);

        Pedido retorno = service.cadastrar(pedidoTeste, new Event());

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals("Produto Cadastro", retorno.getProduto());

        Mockito.verify(pedido, Mockito.times(1))
                .save(Mockito.any(Pedido.class));
    }

    @Test
    @DisplayName("Testar função editar se altera data de modificação")
    void testarFuncaoEditarSeAlteraDataDeModificacao() {

        Pedido pedidoTeste = new Pedido(
                1,
                "Produto Editado",
                "Descricao Editada",
                200.0,
                true,
                false,
                LocalDateTime.now().plusDays(2),
                null,
                new Entrega(),
                new Pagamento(),
                new Cliente(),
                new Endereco(),
                null
        );

        Mockito.when(pedido.findByIdAndIsAtivoTrue(Mockito.any())).thenReturn(pedidoTeste);
        Mockito.when(pedido.save(Mockito.any(Pedido.class))).thenReturn(pedidoTeste);

        Pedido retorno = service.editar(pedidoTeste);

        Assertions.assertNotNull(retorno);
        Assertions.assertNotNull(pedidoTeste.getDataModificacao());

        Mockito.verify(historico, Mockito.times(1))
                .save(Mockito.any());

        Mockito.verify(pedido, Mockito.times(1))
                .save(Mockito.any(Pedido.class));
    }

    @Test
    @DisplayName("Testar função deletar se desativa pedido existente")
    void testarFuncaoDeletarSeDesativaPedidoExistente() {

        Pedido pedidoTeste = new Pedido(
                1,
                "Produto",
                "Descricao",
                50.0,
                true,
                false,
                LocalDateTime.now(),
                null,
                new Entrega(),
                new Pagamento(),
                new Cliente(),
                new Endereco(),
                null
        );

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1))
                .thenReturn(pedidoTeste);

        Mockito.when(pedido.desativarPedido(1))
                .thenReturn(1);

        Integer retorno = service.deletar(1);

        Assertions.assertEquals(1, retorno);

        Mockito.verify(pedido, Mockito.times(1))
                .desativarPedido(1);
    }

    @Test
    @DisplayName("Testar função deletar se lança ErroException para pedido inexistente")
    void testarFuncaoDeletarSeRetornaZeroParaPedidoInexistente() {

        RuntimeException excecao = Assertions.assertThrows(
                RuntimeException.class,
                () -> service.deletar(999)
        );

        Assertions.assertEquals("Houve um erro durante a deleção do Pedido.", excecao.getMessage());

        Mockito.verify(pedido, Mockito.never())
                .desativarPedido(Mockito.anyInt());
    }

    @Test
    @DisplayName("Testar função editar se salva histórico corretamente")
    void testarFuncaoEditarSeSalvaHistoricoCorretamente() {

        Pedido pedidoTeste = new Pedido(
                1,
                "Produto Histórico",
                "Descricao Histórico",
                75.0,
                true,
                false,
                LocalDateTime.now().plusDays(1),
                null,
                new Entrega(),
                new Pagamento(),
                new Cliente(),
                new Endereco(),
                null
        );

        Mockito.when(pedido.findByIdAndIsAtivoTrue(Mockito.any()))
                .thenReturn(pedidoTeste);
        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
                .thenReturn(pedidoTeste);

        service.editar(pedidoTeste);

        Mockito.verify(historico, Mockito.times(1))
                .save(Mockito.any());

        Mockito.verify(pedido, Mockito.times(1))
                .save(Mockito.any(Pedido.class));
    }

    @Test
    @DisplayName("Testar se editar atualiza dataModificacao")
    void testarSeEditarAtualizaDataModificacao() {

        LocalDateTime dataAntiga = LocalDateTime.of(2025, 1, 1, 10, 0);

        Pedido pedidoTeste = new Pedido();

        pedidoTeste.setDataModificacao(dataAntiga);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(Mockito.any()))
                .thenReturn(pedidoTeste);
        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
                .thenReturn(pedidoTeste);

        service.editar(pedidoTeste);

        Assertions.assertNotEquals(
                dataAntiga,
                pedidoTeste.getDataModificacao()
        );
    }

    @Test
    @DisplayName("Testar se editar envia pedido correto para save")
    void testarSeEditarEnviaPedidoCorretoParaSave() {

        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setProduto("Produto Teste");

        Mockito.when(pedido.findByIdAndIsAtivoTrue(Mockito.any()))
                .thenReturn(pedidoTeste);
        Mockito.when(pedido.save(Mockito.any(Pedido.class)))
                .thenReturn(pedidoTeste);

        service.editar(pedidoTeste);

        Mockito.verify(pedido)
                .save(pedidoTeste);
    }

    @Test
    @DisplayName("Não deve marcar como reagendado quando a data permanecer igual")
    void naoDeveMarcarComoReagendadoSemAlteracaoDeData() {
        LocalDateTime dataPedido = LocalDateTime.now().plusDays(2);
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setDataPedido(dataPedido);
        pedidoExistente.setIsReagendado(false);

        Pedido alteracoes = new Pedido();
        alteracoes.setId(1);
        alteracoes.setDataPedido(dataPedido);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoExistente);
        Mockito.when(pedido.save(pedidoExistente)).thenReturn(pedidoExistente);

        Pedido resultado = service.editar(alteracoes);

        Assertions.assertFalse(resultado.getIsReagendado());
    }

    @Test
    @DisplayName("Deve marcar como reagendado quando a data ou horário mudar")
    void deveMarcarComoReagendadoComAlteracaoDeData() {
        LocalDateTime dataOriginal = LocalDateTime.now().plusDays(2);
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setDataPedido(dataOriginal);
        pedidoExistente.setIsReagendado(false);

        Pedido alteracoes = new Pedido();
        alteracoes.setId(1);
        alteracoes.setDataPedido(dataOriginal.plusHours(1));

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoExistente);
        Mockito.when(pedido.save(pedidoExistente)).thenReturn(pedidoExistente);

        Pedido resultado = service.editar(alteracoes);

        Assertions.assertTrue(resultado.getIsReagendado());
    }

    @Test
    @DisplayName("Deve preservar a identificação de um pedido já reagendado")
    void devePreservarPedidoJaReagendado() {
        LocalDateTime dataPedido = LocalDateTime.now().plusDays(2);
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setDataPedido(dataPedido);
        pedidoExistente.setIsReagendado(true);

        Pedido alteracoes = new Pedido();
        alteracoes.setId(1);
        alteracoes.setDataPedido(dataPedido);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoExistente);
        Mockito.when(pedido.save(pedidoExistente)).thenReturn(pedidoExistente);

        Pedido resultado = service.editar(alteracoes);

        Assertions.assertTrue(resultado.getIsReagendado());
    }

    @Test
    @DisplayName("Deve preservar os status e a situação ativa durante a edição")
    void devePreservarStatusDuranteEdicao() {
        LocalDateTime dataPedido = LocalDateTime.now().plusDays(2);
        Pagamento pagamentoAtual = new Pagamento(2, "Pago", null, null);
        Entrega entregaAtual = new Entrega(2, "Em trânsito", null, null);
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setDataPedido(dataPedido);
        pedidoExistente.setIsAtivo(true);
        pedidoExistente.setPagamento(pagamentoAtual);
        pedidoExistente.setEntrega(entregaAtual);

        Pedido alteracoes = new Pedido();
        alteracoes.setId(1);
        alteracoes.setDataPedido(dataPedido);
        alteracoes.setIsAtivo(false);
        alteracoes.setPagamento(new Pagamento(1, "Pendente", null, null));
        alteracoes.setEntrega(new Entrega(1, "Pendente", null, null));

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoExistente);
        Mockito.when(pedido.save(pedidoExistente)).thenReturn(pedidoExistente);

        Pedido resultado = service.editar(alteracoes);

        Assertions.assertTrue(resultado.getIsAtivo());
        Assertions.assertSame(pagamentoAtual, resultado.getPagamento());
        Assertions.assertSame(entregaAtual, resultado.getEntrega());
    }

    @Test
    @DisplayName("Deve atualizar os status do pedido ativo")
    void deveAtualizarStatusDoPedidoAtivo() {
        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setId(1);
        pedidoTeste.setIsAtivo(true);
        pedidoTeste.setIsReagendado(false);
        pedidoTeste.setPagamento(new Pagamento(1, "Pendente", null, null));
        pedidoTeste.setEntrega(new Entrega(1, "Pendente", null, null));

        Pagamento pagamentoPago = new Pagamento(2, "Pago", null, null);
        Entrega entregaEmTransito = new Entrega(2, "Em trânsito", null, null);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoTeste);
        Mockito.when(pagamentoRepository.findById(2)).thenReturn(Optional.of(pagamentoPago));
        Mockito.when(entregaRepository.findById(2)).thenReturn(Optional.of(entregaEmTransito));
        Mockito.when(pedido.save(pedidoTeste)).thenReturn(pedidoTeste);

        Pedido resultado = service.atualizarStatus(1, 2, 2);

        Assertions.assertSame(pagamentoPago, resultado.getPagamento());
        Assertions.assertSame(entregaEmTransito, resultado.getEntrega());
        Assertions.assertNotNull(resultado.getDataModificacao());
        Assertions.assertFalse(resultado.getIsReagendado());
        Mockito.verify(historico).save(Mockito.any(HistoricoPedido.class));
        Mockito.verify(pedido).save(pedidoTeste);
        Mockito.verifyNoInteractions(calendario);
    }

    @Test
    @DisplayName("Não deve criar histórico quando os status não mudarem")
    void naoDeveCriarHistoricoQuandoStatusNaoMudar() {
        Pagamento pagamentoPendente = new Pagamento(1, "Pendente", null, null);
        Entrega entregaPendente = new Entrega(1, "Pendente", null, null);
        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setId(1);
        pedidoTeste.setPagamento(pagamentoPendente);
        pedidoTeste.setEntrega(entregaPendente);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoTeste);
        Mockito.when(pagamentoRepository.findById(1)).thenReturn(Optional.of(pagamentoPendente));
        Mockito.when(entregaRepository.findById(1)).thenReturn(Optional.of(entregaPendente));

        Pedido resultado = service.atualizarStatus(1, 1, 1);

        Assertions.assertSame(pedidoTeste, resultado);
        Mockito.verifyNoInteractions(historico);
        Mockito.verify(pedido, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve rejeitar status cancelado na atualização rápida")
    void deveRejeitarStatusCancelado() {
        Pedido pedidoTeste = new Pedido();
        pedidoTeste.setId(1);

        Mockito.when(pedido.findByIdAndIsAtivoTrue(1)).thenReturn(pedidoTeste);
        Mockito.when(pagamentoRepository.findById(3))
                .thenReturn(Optional.of(new Pagamento(3, "Cancelado", null, null)));
        Mockito.when(entregaRepository.findById(1))
                .thenReturn(Optional.of(new Entrega(1, "Pendente", null, null)));

        Assertions.assertThrows(
                StatusPedidoInvalidoException.class,
                () -> service.atualizarStatus(1, 3, 1)
        );

        Mockito.verify(pedido, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve rejeitar atualização de status de pedido inativo ou inexistente")
    void deveRejeitarStatusDePedidoInexistente() {
        Mockito.when(pedido.findByIdAndIsAtivoTrue(99)).thenReturn(null);

        Assertions.assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.atualizarStatus(99, 1, 1)
        );

        Mockito.verifyNoInteractions(pagamentoRepository, entregaRepository);
    }
}
