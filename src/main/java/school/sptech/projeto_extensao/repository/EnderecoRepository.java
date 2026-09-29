package school.sptech.projeto_extensao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Page<Endereco> findByClienteId(Integer idCliente, Pageable pageable);
}
