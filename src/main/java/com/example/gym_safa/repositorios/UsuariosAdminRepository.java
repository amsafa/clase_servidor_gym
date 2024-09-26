package com.example.gym_safa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Usuarios_Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosAdminRepository extends JpaRepository<Usuarios_Admin, Integer> {
}
