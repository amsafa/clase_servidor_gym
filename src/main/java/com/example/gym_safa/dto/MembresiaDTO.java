package com.example.gym_safa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaDTO {
    private Integer id;
    private Integer duracionMeses;
    private Double precio;
}
