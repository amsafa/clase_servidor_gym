package com.example.gym_safa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocioDTO {
    private Integer id;
    private String nombre;
    private String DNI;
    private LocalDate fecha_nacimiento;
    private Integer cuenta_bancaria;
    private String telefono;
    private String email;
    private Integer membresiaId;
    private LocalDate fecha_registro;
}
