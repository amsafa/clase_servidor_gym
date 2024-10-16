package com.example.gym_safa.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaDTO {

   private Integer id;
   private Integer socioId;
   private LocalDateTime fechaEntrada;
   private LocalDateTime fechaSalida;

}