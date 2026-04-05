package school.sptech.projeto_extensao.dto;

import school.sptech.projeto_extensao.model.Admin;

public class AdminMapper {

    public static Admin of(AdminCriacaoDto adminCriacaoDto) {
        Admin admin = new Admin();

        admin.setUsuario(adminCriacaoDto.getUsuario());
        admin.setSenha(adminCriacaoDto.getSenha());
        return admin;
    }

    public static Admin of(AdminLoginDto adminLoginDto) {
        Admin admin = new Admin();

        admin.setUsuario(adminLoginDto.getUsuario());
        admin.setSenha(adminLoginDto.getSenha());
        return admin;
    }

    public static AdminTokenDto of(Admin admin, String token) {
        AdminTokenDto adminTokenDto = new AdminTokenDto();

        adminTokenDto.setId(admin.getId());
        adminTokenDto.setUsuario(admin.getUsuario());
        adminTokenDto.setToken(token);

        return adminTokenDto;
    }

    public static AdminSessaoDto ofSessao(AdminTokenDto tokenDto) {
        AdminSessaoDto dto = new AdminSessaoDto();

        dto.setId(tokenDto.getId());
        dto.setUsuario(tokenDto.getUsuario());

        return dto;
    }

    public static AdminListarDto of(Admin admin) {
        AdminListarDto adminListarDto = new AdminListarDto();

        adminListarDto.setId(admin.getId());
        adminListarDto.setUsuario(admin.getUsuario());

        return adminListarDto;
    }
}
