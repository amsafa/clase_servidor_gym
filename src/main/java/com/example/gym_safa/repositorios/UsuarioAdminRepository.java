package com.example.gym_safa.repositorios;

import com.example.gym_safa.modelos.UsuarioAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAdminRepository extends JpaRepository<UsuarioAdmin, Integer> {

}
