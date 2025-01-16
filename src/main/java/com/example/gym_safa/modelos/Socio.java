package com.example.gym_safa.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "socio", schema = "gym_db")
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



    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fecha_registro;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vencimiento> vencimientos;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;








}
