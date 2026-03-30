package school.sptech.projeto_extensao.service;

import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.model.Admin;
import school.sptech.projeto_extensao.repository.AdminRepository;

@Service
public class AdminService {
    private final AdminRepository service;

    public AdminService(AdminRepository service) {
        this.service = service;
    }

    public Boolean verificarAdmin(Admin admin){
        return admin.getUsuario() != null &&
                !admin.getUsuario().isBlank() &&
                admin.getSenha() != null &&
                !admin.getSenha().isBlank();
    }
}
