package com.example.gym_safa.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Asistencias;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciasRepository extends JpaRepository<Asistencias, Integer> {
}
