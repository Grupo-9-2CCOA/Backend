package school.sptech.projeto_extensao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projeto_extensao.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
