package com.example.gym_safa;

import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.enumerados.DiaSemana;
import com.example.gym_safa.servicios.AsistenciaService;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AsistenciaTest {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private SocioService socioService;

    @Test
    void testCrearAsistencia(){
        //Recuperamos un socio de la base de datos
        Socio socio = socioService.getSociosById(1);

        Asistencia asistencia = new Asistencia();
        asistencia.setSocio(socio);
        asistencia.setFecha(LocalDate.of(2021, 10, 10));
        asistencia.setDias_semana(DiaSemana.LUNES);

        Asistencia asistenciaGuardado = asistenciaService.saveAsistencias(asistencia);
        System.out.println(asistenciaGuardado.toString());
    }

    @Test
    void testEditarAsistencia(){
        Asistencia asistencia = asistenciaService.getAsistenciasById(1);
        asistencia.setFecha(LocalDate.of(2021, 10, 10));
        asistencia.setDias_semana(DiaSemana.MARTES);

        Asistencia asistenciaGuardado = asistenciaService.saveAsistencias(asistencia);
        System.out.println(asistenciaGuardado.toString());
    }

    @Test
    void testEliminarAsistencia(){
        asistenciaService.deleteAsistencias(1);
    }

    @Test
    void testBuscarAsistencia(){
        Asistencia asistencia = asistenciaService.getAsistenciasById(6);
        System.out.println(asistencia.getId());
    }

}
