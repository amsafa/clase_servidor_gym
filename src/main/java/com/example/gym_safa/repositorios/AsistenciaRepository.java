package com.example.gym_safa.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Asistencia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    // Tengo que rehacerlo junto al metodo getAsistenciaResumenBySocioId
    List<Asistencia> findBySocioId(Integer socioId);
}
