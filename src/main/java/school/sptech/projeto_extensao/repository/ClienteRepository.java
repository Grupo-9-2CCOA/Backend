package school.sptech.projeto_extensao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Page<Cliente> findAllByIsAtivoTrue(Pageable pageable);

    Page<Cliente> findAllByIsAtivoFalse(Pageable pageable);

    Page<Cliente> findByIsAtivoTrueAndTelefoneContaining(String telefone, Pageable pageable);

    Page<Cliente> findByIsAtivoTrueAndNomeContainingIgnoreCaseOrIsAtivoTrueAndTelefoneContaining(String nome, String telefone, Pageable pageable);
}
