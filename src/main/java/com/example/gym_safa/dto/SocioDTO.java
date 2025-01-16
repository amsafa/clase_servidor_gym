package com.example.gym_safa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocioDTO {
    private Integer id;
    private String nombre;
    private String dni;
    private LocalDate fechaNacimiento;
    private Integer cuentaBancaria;
    private String telefono;
    private String email;
    private LocalDate fechaRegistro;

    // Relación con vencimientos y pagos
    private List<VencimientoDTO> vencimientos;
    private List<PagoDTO> pagos;

    // Getters y setters
}
