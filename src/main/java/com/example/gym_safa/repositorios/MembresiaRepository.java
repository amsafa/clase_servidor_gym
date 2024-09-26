package com.example.gym_safa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gym_safa.modelos.Membresias;
import org.springframework.stereotype.Repository;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresias, Integer> {
}