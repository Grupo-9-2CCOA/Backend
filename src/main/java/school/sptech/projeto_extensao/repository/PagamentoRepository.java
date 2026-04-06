package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
