package com.example.gym_safa.modelos;


import com.example.gym_safa.Enumerados.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_admin", schema = "gym_db", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Usuarios_Admin {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;


    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TipoUsuario rol;
}
