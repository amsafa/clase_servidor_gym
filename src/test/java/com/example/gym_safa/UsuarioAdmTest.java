package com.example.gym_safa;


import com.example.gym_safa.modelos.UsuarioAdmin;
import com.example.gym_safa.servicios.UsuarioAdmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gym_safa.enumerados.TipoUsuario;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class UsuarioAdmTest {

    @Autowired
    private UsuarioAdmService usuarioAdmService;

    @Test
    void testCrearUsuarioAdm() {
        UsuarioAdmin usuarioAdmin = new UsuarioAdmin();
        usuarioAdmin.setUsuario("admin");
        usuarioAdmin.setClave("admin");
        usuarioAdmin.setRol(TipoUsuario.CLIENTE);
        usuarioAdmService.saveUsuariosAdmin(usuarioAdmin);
    }

    @Test
    void testBuscarUsuarioAdm() {
        UsuarioAdmin usuarioAdmin = usuarioAdmService.getUsuariosAdminById(1);
        System.out.println(usuarioAdmin);
    }

    @Test
    void testModificarUsuarioAdm() {
        UsuarioAdmin usuarioAdmin = usuarioAdmService.getUsuariosAdminById(3);
        usuarioAdmin.setUsuario("pana");

        UsuarioAdmin usuarioAdminGuardado = usuarioAdmService.saveUsuariosAdmin(usuarioAdmin);
        System.out.println(usuarioAdminGuardado);

    }

    @Test
    void testEliminarUsuarioAdm() {
        usuarioAdmService.deleteUsuariosAdmin(3);
    }

}
