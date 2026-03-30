package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
