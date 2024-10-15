package com.example.gym_safa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Integer id;
    private Integer socioId;
    private Double monto;
    private LocalDate fecha;
    private Integer tipoPago;

}
