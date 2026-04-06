package school.sptech.projeto_extensao.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.projeto_extensao.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.isAtivo = false WHERE p.id = :id")
    int desativarPedido(@Param("id") Integer id);
}
