package school.sptech.projeto_extensao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=never"
})
class PedidoRepositoryTest {

    private static final LocalDateTime INICIO_PERIODO = LocalDateTime.of(2026, 1, 10, 0, 0);
    private static final LocalDateTime FIM_PERIODO = LocalDateTime.of(2026, 1, 20, 23, 59);
    private static final LocalDateTime FORA_DO_PERIODO = LocalDateTime.of(2026, 1, 1, 12, 0);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveClassificarClientesNosLimitesDoMinimoComPedidoNoPeriodo() {
        criarClienteComPedidos("Cliente com um pedido", 1, true);
        criarClienteComPedidos("Cliente com dois pedidos", 2, true);
        criarClienteComPedidos("Cliente com três pedidos", 3, true);
        criarClienteComPedidos("Cliente com quatro pedidos", 4, true);
        criarClienteComPedidos("Cliente sem pedido no período", 4, false);
        entityManager.flush();
        entityManager.clear();

        Integer fidelizados = pedidoRepository.countClientesFidelizadosComPedidoNoPeriodo(
                INICIO_PERIODO, FIM_PERIODO, 3);
        Integer naoFidelizados = pedidoRepository.countClientesNaoFidelizadosComPedidoNoPeriodo(
                INICIO_PERIODO, FIM_PERIODO, 3);
        Integer novos = pedidoRepository.countClientesNovosNoPeriodo(INICIO_PERIODO, FIM_PERIODO);

        assertEquals(2, fidelizados);
        assertEquals(2, naoFidelizados);
        assertEquals(1, novos);
    }

    @Test
    void deveRegistrarEConsultarCancelamentoPelaDataModificacao() {
        Cliente cliente = criarCliente("Cliente cancelamento");
        Pedido pedido = criarPedido(cliente, FORA_DO_PERIODO);
        entityManager.flush();
        entityManager.clear();
        LocalDateTime antesCancelamento = LocalDateTime.now().minusSeconds(1);

        assertEquals(1, pedidoRepository.desativarPedido(pedido.getId()));
        entityManager.flush();
        entityManager.clear();
        LocalDateTime depoisCancelamento = LocalDateTime.now().plusSeconds(1);

        Pedido pedidoCancelado = pedidoRepository.findById(pedido.getId()).orElseThrow();
        assertFalse(pedidoCancelado.getIsAtivo());
        assertNotNull(pedidoCancelado.getDataModificacao());
        assertTrue(!pedidoCancelado.getDataModificacao().isBefore(antesCancelamento));
        assertTrue(!pedidoCancelado.getDataModificacao().isAfter(depoisCancelamento));
        assertEquals(1, pedidoRepository.countPedidosCanceladosByDataModificacaoBetween(
                antesCancelamento, depoisCancelamento));

        List<Pedido> cancelados = pedidoRepository.findTop15CanceladosByDataModificacaoBetween(
                antesCancelamento, depoisCancelamento);

        assertEquals(1, cancelados.size());
        assertEquals(pedido.getId(), cancelados.get(0).getId());
    }

    private void criarClienteComPedidos(String nome, int quantidadePedidos, boolean possuiPedidoNoPeriodo) {
        Cliente cliente = criarCliente(nome);

        for (int indice = 0; indice < quantidadePedidos; indice++) {
            LocalDateTime dataCriacao = possuiPedidoNoPeriodo && indice == 0
                    ? INICIO_PERIODO.plusDays(1)
                    : FORA_DO_PERIODO;
            criarPedido(cliente, dataCriacao);
        }
    }

    private Cliente criarCliente(String nome) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(nome);
        cliente.setTelefone(nome);
        cliente.setAtivo(true);
        return entityManager.persist(cliente);
    }

    private Pedido criarPedido(Cliente cliente, LocalDateTime dataCriacao) {
        Pedido pedido = new Pedido();
        pedido.setProduto("Produto");
        pedido.setDescricao("Descrição");
        pedido.setValor(10.0);
        pedido.setIsAtivo(true);
        pedido.setIsReagendado(false);
        pedido.setDataPedido(dataCriacao);
        pedido.setDataCriacao(dataCriacao);
        pedido.setCliente(cliente);
        return entityManager.persist(pedido);
    }
}
