package com.example.gym_safa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Pagos;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository  extends JpaRepository<Pagos, Integer> {
}
