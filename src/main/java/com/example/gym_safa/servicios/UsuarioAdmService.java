package com.example.gym_safa.servicios;


import com.example.gym_safa.repositorios.UsuarioAdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gym_safa.modelos.UsuarioAdmin;

@Service
@AllArgsConstructor
public class UsuarioAdmService {

    private UsuarioAdminRepository usuarioAdminRepository;

    /**
     * Busca un usuario por su id
     * @param id
     * @return
     */
    public UsuarioAdmin getUsuariosAdminById(Integer id) {
        return usuarioAdminRepository.findById(id).orElse(null);
    }


    /**
     * Guarda un usuario nuevo o modifica
     * @param usuario_admin
     * @return
     */
    public UsuarioAdmin saveUsuariosAdmin(UsuarioAdmin usuario_admin) {
        return usuarioAdminRepository.save(usuario_admin);
    }

    /**
     * Elimina un usuario por su id
     * @param id
     */
    public void deleteUsuariosAdmin(Integer id) {
        usuarioAdminRepository.deleteById(id);
    }

}
