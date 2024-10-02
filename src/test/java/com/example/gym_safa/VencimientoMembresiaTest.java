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
        vencimientoMembresia.setFecha_fin(LocalDate.now());
        vencimientoMembresia.setEstado(Estado.VENCIDO);

        VencimientoMembresia vencimientoMembresiaGuardado = vencimientoMembresiaService.saveVencimientosMembresias(vencimientoMembresia);

    }
}
