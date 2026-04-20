package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.projeto_extensao.model.HistoricoPedido;

@Repository
public interface HistoricoPedidoRepository extends JpaRepository<HistoricoPedido, Integer> {
}
