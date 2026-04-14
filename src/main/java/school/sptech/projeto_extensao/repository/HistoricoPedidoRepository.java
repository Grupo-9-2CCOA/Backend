package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.HistoricoPedido;

public interface HistoricoPedidoRepository extends JpaRepository<HistoricoPedido, Integer> {
}
