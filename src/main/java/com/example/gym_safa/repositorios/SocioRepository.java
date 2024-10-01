package com.example.gym_safa.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Socio;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer> {

}
