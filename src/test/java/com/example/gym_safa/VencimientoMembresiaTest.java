package com.example.gym_safa;


import com.example.gym_safa.modelos.VencimientoMembresia;
import com.example.gym_safa.servicios.VencimientoMembresiaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.servicios.SocioService;

import java.time.LocalDate;

@SpringBootTest
public class VencimientoMembresiaTest {

    @Autowired
    private VencimientoMembresiaService vencimientoMembresiaService;

    @Autowired
    private SocioService socioService;



@Test
void testCrearVencimientoMembresia() {
    VencimientoMembresia vencimientoMembresia = new VencimientoMembresia();

    vencimientoMembresia.setFecha_inicio(LocalDate.now());
    vencimientoMembresia.setFecha_fin(LocalDate.now().plusMonths(1)); // Establecer una fecha de fin válida
    vencimientoMembresia.setEstado(Estado.VENCIDO);

    // Recuperar un socio existente de la base de datos
    Socio socio = socioService.getSociosById(1); // Asegúrate de que el ID del socio exista
    socio = socioService.saveSocios(socio); // Merge the Socio entity to ensure it is managed
    vencimientoMembresia.setSocio(socio);

    VencimientoMembresia vencimientoMembresiaGuardado = vencimientoMembresiaService.saveVencimientosMembresias(vencimientoMembresia);
    System.out.println(vencimientoMembresiaGuardado.toString());
}
}
