package com.example.gym_safa.servicios;


import com.example.gym_safa.repositorios.UsuarioAdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gym_safa.modelos.Usuario;

@Service
@AllArgsConstructor
public class UsuarioAdmService {

    private UsuarioAdminRepository usuarioAdminRepository;

    /**
     * Busca un usuario por su id
     * @param id
     * @return
     */
    public Usuario getUsuariosAdminById(Integer id) {
        return usuarioAdminRepository.findById(id).orElse(null);
    }


    /**
     * Guarda un usuario nuevo o modifica
     * @param usuario_admin
     * @return
     */
    public Usuario saveUsuariosAdmin(Usuario usuario_admin) {
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
