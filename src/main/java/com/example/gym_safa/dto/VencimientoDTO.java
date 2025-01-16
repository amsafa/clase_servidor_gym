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
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private String mensaje;

    // Relaciones
    private Integer socioId;       // ID del socio asociado
    private Integer membresiaId;   // ID de la membresía asociada

    // Getters y setters
}

