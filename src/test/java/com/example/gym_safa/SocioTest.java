package com.example.gym_safa;

import com.example.gym_safa.servicios.SocioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gym_safa.modelos.Socio;
import java.time.LocalDate;
import java.util.List;


@SpringBootTest
public class SocioTest {

    @Autowired
    private SocioService socioService;




    @Test
    void testCrearSocios(){
        Socio socios = new Socio();
        socios.setNombre("Petra");
        socios.setDNI("12345678P");
        socios.setFecha_nacimiento(LocalDate.of(1995, 10, 10));
        socios.setCuenta_bancaria(123056789);
        socios.setTelefono("123456089");
        socios.setEmail("petra@gmail.com");
        socios.setMembresia(null);
        socios.setFecha_registro(LocalDate.of(2021, 10, 10));

        Socio sociosGuardado = socioService.saveSocios(socios);
        System.out.println(sociosGuardado.toString());

    }

    @Test

    void testEditarSocios() {
        Socio socios = socioService.getSociosById(2);
        socios.setNombre("Petra");
        socios.setDNI("11111111P");
        socios.setFecha_nacimiento(LocalDate.of(1995, 10, 10));
        socios.setCuenta_bancaria(123056000);
        socios.setTelefono("123456000");
        socios.setEmail("petra@live.com");
        socios.setMembresia(null);
        socios.setFecha_registro(LocalDate.of(2021, 10, 10));

        Socio sociosGuardado = socioService.saveSocios(socios);
        System.out.println(sociosGuardado.toString());
    }
    @Test
    void testElimnarSocios(){
        socioService.deleteSocios(2);
    }

    @Test
    void testBuscarTodasSocios(){
        List<Socio> socios = socioService.getAllSocios();
        for(Socio s: socios){
            System.out.println(s.getNombre());
        }
    }
}