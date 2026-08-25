package school.sptech.projeto_extensao.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.sptech.projeto_extensao.model.Cliente;
import school.sptech.projeto_extensao.model.Pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findAllByDataPedidoBetweenOrderByDataCriacaoDesc(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Pedido> findAllByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.isAtivo = false AND p.dataCriacao BETWEEN :dataInicio AND :dataFim")
    Integer countPedidosCanceladosByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.isReagendado = true AND p.dataCriacao BETWEEN :dataInicio AND :dataFim")
    Integer countPedidosReagendadosByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT COUNT(DISTINCT p.cliente.id) FROM Pedido p WHERE p.dataCriacao BETWEEN :dataInicio AND :dataFim " +
            "AND (SELECT COUNT(p2) FROM Pedido p2 WHERE p2.cliente.id = p.cliente.id) > 1")
    Integer countClientesFidelizadosComPedidoNoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT COUNT(DISTINCT p.cliente.id) FROM Pedido p WHERE p.dataCriacao BETWEEN :dataInicio AND :dataFim " +
            "AND (SELECT COUNT(p2) FROM Pedido p2 WHERE p2.cliente.id = p.cliente.id) = 1")
    Integer countClientesNovosNoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT p FROM Pedido p WHERE p.isAtivo = false AND p.dataCriacao BETWEEN :dataInicio AND :dataFim ORDER BY p.dataCriacao DESC LIMIT 15")
    List<Pedido> findTop15CanceladosByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT p FROM Pedido p WHERE p.isReagendado = true AND p.dataCriacao BETWEEN :dataInicio AND :dataFim ORDER BY p.dataCriacao DESC LIMIT 15")
    List<Pedido> findTop15ReagendadosByDataCriacaoBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT DISTINCT p.cliente FROM Pedido p WHERE p.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<Cliente> findTop15ClientesComPedidoNoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.isAtivo = false WHERE p.id = :id")
    int desativarPedido(@Param("id") Integer id);

    List<Pedido> findAllByIsAtivoTrueAndDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Pedido> findAllByIsAtivoTrue();

    List<Pedido> findByEnderecoId(Integer enderecoId);

    @Query("""
        SELECT p.id
        FROM Pedido p
        WHERE p.endereco.id = :enderecoId
          AND (p.isAtivo IS NULL OR p.isAtivo = true)
          AND (p.entrega IS NULL OR p.entrega.id <> :entregueId)
        """)
    List<Integer> findIdsByEnderecoIdWithEntregaNot(
            @Param("enderecoId") Integer enderecoId,
            @Param("entregueId") Integer entregueId
    );

    Pedido findByIdAndIsAtivoTrue(Integer id);
}
