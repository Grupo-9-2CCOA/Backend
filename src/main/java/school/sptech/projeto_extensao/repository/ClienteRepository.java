package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAllByAtivoTrue();

    List<Cliente> findAllByAtivoFalse();

    List<Cliente> findByAtivoTrueAndTelefoneContaining(String telefone);

    List<Cliente> findByAtivoTrueAndNomeContainingIgnoreCaseOrAtivoTrueAndTelefoneContaining(String nome, String telefone);
}
