package com.example.gym_safa.modelos;

import com.example.gym_safa.enumerados.TipoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "pago", schema = "gym_db")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity

public class Pago {

    @Id
    @Column(name = "pago_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.ORDINAL)
    @Column (name = "tipo_pago")
    private TipoPago tipo_pago;

}

