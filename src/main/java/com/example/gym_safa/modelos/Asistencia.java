package com.example.gym_safa.modelos;


import com.example.gym_safa.enumerados.DiaSemana;
import jakarta.persistence.*;
import lombok.*;




import java.time.LocalDate;

@Entity
@Table(name = "asistencias", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asistencia_id" )
    private Integer id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @Column(name = "fecha_asistencia", nullable = false)
    private LocalDate fecha;

    @Column (name = "dias_por_semana")
    @Enumerated(EnumType.ORDINAL)
    private DiaSemana dias_semana;














}
