package org.example.gym_safa;

import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.dto.MembresiaDTO;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.repositorios.MembresiaRepository;
import com.example.gym_safa.servicios.MembresiaService;
import com.example.gym_safa.servicios.SocioService;
import com.example.gym_safa.enumerados.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
public class MembresiaServiceTest {

    @Autowired
    private MembresiaService service;


    @Autowired
    private MembresiaRepository repository;

    @MockBean
    private MembresiaRepository membresiaRepository;



    @BeforeEach
    public void InicializarBaseDatos()  {



        Membresia membresia1 = new Membresia();
        membresia1.setNombre(NombreMembresia.Basico);
        membresia1.setPrecio(100.0);
        membresia1.setDuracionMeses(3);
        repository.save(membresia1);

        Membresia membresia2 = new Membresia();
        membresia2.setNombre(NombreMembresia.Intermedio);
        membresia2.setPrecio(200.0);
        membresia2.setDuracionMeses(6);
        repository.save(membresia2);


    }

    @Test
    public void testGetAllMembresias() {
        //GIVEN

        //WHEN
       List <MembresiaDTO> membresias = service.getAllMembresias();

        //THEN
        assertEquals(2, membresias.size());
    }

    @Test
    public void testGetAllMembresiasNegativo() {
        //GIVEN: Creamos una membresía con duración 0
        Membresia membresia3 = new Membresia();
        membresia3.setNombre(NombreMembresia.Invencible);
        membresia3.setPrecio(300.0);
        membresia3.setDuracionMeses(0);

        //Simulamos que la membresía se ha guardado en la base de datos
        when(membresiaRepository.findAll()).thenReturn(List.of(membresia3));

        //WHEN & THEN: Verificamos que se lanza una excepción al intentar obtener todas las membresías

        Exception excepción = assertThrows(IllegalArgumentException.class, () -> {
            service.getAllMembresias();
        });


      // Validamos el mensaje de la excepción
        assertEquals("La duracion de la membresia no puede ser 0", excepción.getMessage());
    }











}