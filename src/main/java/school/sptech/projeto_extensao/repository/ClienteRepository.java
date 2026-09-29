package school.sptech.projeto_extensao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Page<Cliente> findAllByAtivoTrue(Pageable pageable);

    Page<Cliente> findAllByAtivoFalse(Pageable pageable);

    Page<Cliente> findByAtivoTrueAndTelefoneContaining(String telefone, Pageable pageable);

    Page<Cliente> findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining(String nome, String telefone, Pageable pageable);
}
