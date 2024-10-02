package com.example.gym_safa;


import com.example.gym_safa.modelos.Membresia;


import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.servicios.MembresiaService;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gym_safa.enumerados.NombreMembresia;


@SpringBootTest
public class MembresiaTest {

    @Autowired
    private MembresiaService membresiaService;

    @Autowired
    private SocioService socioService;

    @Test
    void testCrearMembresia(){
        //Recuperamos un socio de la base de datos
        Socio socio = socioService.getSociosById(1);

        Membresia membresia = new Membresia();
        membresia.setNombre(NombreMembresia.BASIC);
        membresia.setDuracion_meses(1);
        membresia.setPrecio(50.00);


        Membresia membresiaGuardado = membresiaService.saveMembresias(membresia);
        System.out.println(membresiaGuardado.toString());
    }

    @Test
    void testEditarMembresia() {
        Membresia membresia = membresiaService.getMembresiasById(2);
        membresia.setNombre(NombreMembresia.INTERMEDIO);
        membresia.setDuracion_meses(3);
        membresia.setPrecio(100.00);
    }

    @Test
    void testEliminarMembresia(){
        membresiaService.deleteMembresias(1);
    }

    @Test
    void testBuscarMembresia() {
        Membresia membresia = membresiaService.getMembresiasById(2);
        System.out.println(membresia.getId());
    }
}
