package com.example.gym_safa.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "socios", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "socio_id" )
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "dni", unique = true, nullable = false)
    private String DNI;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fecha_nacimiento;

    @Column (name = "cuenta_bancaria", unique = true, nullable = false)
    private Integer cuenta_bancaria;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "correo", unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "membresia_id")
    private Membresia membresia;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fecha_registro;




}
