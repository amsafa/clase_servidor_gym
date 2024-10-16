package com.example.gym_safa.modelos;

import com.example.gym_safa.enumerados.Estado;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "vencimiento", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity

public class Vencimiento {

    @Id
    @Column(name = "vencimiento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @Column(name = "fecha_i", nullable = false)
    private LocalDate fecha_inicio;


    @Column (name = "fecha_f", nullable = false)
    private  LocalDate fecha_fin;

    @Column (name = "estado")
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;


}
