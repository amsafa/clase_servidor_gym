package com.example.gym_safa.repositorios;

import com.example.gym_safa.modelos.Vencimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VencimientoRepository extends  JpaRepository<Vencimiento, Integer> {


    List<Vencimiento> findBySocioId(Integer socioId);

    //Vencimiento findBySocioId(Integer socioId);


}

