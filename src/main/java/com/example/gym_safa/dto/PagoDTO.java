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
    private Double monto;
    private LocalDate fechaPago;
    private String tipoPago;

    // Relaciones
    private Integer socioId;         // ID del socio asociado
    private Integer vencimientoId;   // ID del vencimiento asociado

}

