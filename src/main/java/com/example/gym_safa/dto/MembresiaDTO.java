package com.example.gym_safa.dto;


import com.example.gym_safa.enumerados.NombreMembresia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaDTO {
    private Integer id;
    private NombreMembresia nombre;
    private Integer duracionMeses;
    private Double precio;
}
