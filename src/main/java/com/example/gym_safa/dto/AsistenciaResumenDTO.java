package com.example.gym_safa.dto;

import com.example.gym_safa.modelos.Socio;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsistenciaResumenDTO {
    private Integer socioId;
    private Integer totalDias;
    private Long totalHoras;
}