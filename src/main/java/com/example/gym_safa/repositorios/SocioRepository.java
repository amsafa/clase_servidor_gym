package com.example.gym_safa.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Socio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer> {

    Optional<Socio> findByDNI(String dni);


    Optional<Socio> findByEmail(String email);
}
