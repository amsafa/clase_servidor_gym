package com.example.gym_safa.dto;

import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.modelos.Socio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class VencimientoDTO {
    private Integer id;
    private Socio socio;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String estado;

    // Contructor adicional para id y fecha_fin

    public VencimientoDTO(Integer id, LocalDate fecha_fin) {
        this.id = id;
        this.fecha_fin = fecha_fin;
    }

}
