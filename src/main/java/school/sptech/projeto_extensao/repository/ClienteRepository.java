package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
