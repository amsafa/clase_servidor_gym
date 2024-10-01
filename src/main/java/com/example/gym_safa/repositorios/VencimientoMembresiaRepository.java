package com.example.gym_safa.repositorios;

import com.example.gym_safa.modelos.VencimientoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VencimientoMembresiaRepository extends  JpaRepository<VencimientoMembresia, Integer> {

}
