package com.example.gym_safa.modelos;

import com.example.gym_safa.enumerados.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role rol;
}
