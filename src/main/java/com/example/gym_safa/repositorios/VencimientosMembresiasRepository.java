package com.example.gym_safa.repositorios;

import com.example.gym_safa.modelos.Vencimientos_Membresias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VencimientosMembresiasRepository extends  JpaRepository<Vencimientos_Membresias, Integer> {
}
