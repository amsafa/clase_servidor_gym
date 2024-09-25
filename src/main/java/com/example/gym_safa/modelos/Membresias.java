package com.example.gym_safa.modelos;

import jakarta.persistence.*;
import lombok.*;
import  com.example.gym_safa.Enumerados.*;

@Entity
@Table(name = "membresias", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Membresias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membresia_id" )
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "nombre", nullable = false)
    private NombreMembresia nombre;

    @Column(name = "duracion_meses", nullable = false)
    private Integer duracion_meses;

    @Column(name = "precio", nullable = false)
    private Double precio ;






}
