package com.example.gym_safa.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Socios;
import org.springframework.stereotype.Repository;

@Repository
public interface SociosRepository extends JpaRepository<Socios, Integer> {
}
