package com.example.gym_safa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Pago;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

}
