package com.example.gym_safa.modelos;

import jakarta.persistence.*;
import lombok.*;
import com.example.gym_safa.enumerados.NombreMembresia;

@Entity
@Table(name = "membresia", schema = "gym_db")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membresia_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false)
    private NombreMembresia nombre;

    @Column(name = "duracion_meses", nullable = false)
    private Integer duracionMeses;

    @Column(name = "precio", nullable = false)
    private Double precio;


}