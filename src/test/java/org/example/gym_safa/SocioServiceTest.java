package org.example.gym_safa;

import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.VencimientoRepository;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {SocioService.class})
@ExtendWith(MockitoExtension.class) // Permite usar Mockito

public class SocioServiceTest {

    @Mock
    private VencimientoRepository vencimientoRepository;

    @InjectMocks
    private SocioService socioService;


    @Test
    public void testRenovarAbonoPositivo() {
        //GIVEN: Un socio con un vencimiento activo

        Integer idSocio = 1;

        Vencimiento vencimientoAntiguo = new Vencimiento();
        vencimientoAntiguo.setId(1);
        vencimientoAntiguo.setFecha_inicio(LocalDate.of(2023, 1, 1));
        vencimientoAntiguo.setFecha_fin(LocalDate.of(2024, 1, 31));
        vencimientoAntiguo.setEstado(Estado.ACTIVO);

        List<Vencimiento> vencimientos = new ArrayList<>();
        vencimientos.add(vencimientoAntiguo);

        // Configurar el mock del repositorio
        Mockito.when(vencimientoRepository.findBySocioId(idSocio))
                .thenReturn(vencimientos);

        // WHEN: Se renueva la membresía del socio
        VencimientoDTO resultado = socioService.renovarMembresiaSocio(idSocio);



        // THEN: La membresía debe renovarse correctamente
        Assertions.assertNotNull(resultado, "El resultado no debe ser nulo");
        Assertions.assertEquals(vencimientoAntiguo.getFecha_fin().plusMonths(12), resultado.getFechaFin(),
                "La fecha de fin debe ser 12 meses después del vencimiento anterior");

        // Verificar que el método save fue llamado exactamente una vez
        Mockito.verify(vencimientoRepository, Mockito.times(1)).save(Mockito.any(Vencimiento.class));

        // Verificar que el mensaje de renovación exitosa esté presente
        Assertions.assertEquals("Renovación exitosa", resultado.getMensaje(),
                "El mensaje debe ser 'Renovación exitosa'");

    }

    @Test
    void testRenovarAbonoNegativo() {
        // GIVEN: Un socio sin membresía activa
        Integer socioId = 1;

        // Configurar el mock para que devuelva una lista vacía
        Mockito.when(vencimientoRepository.findBySocioId(socioId)).thenReturn(Collections.emptyList());

        // WHEN & THEN: Se lanza una excepción al intentar renovar la membresía
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            socioService.renovarMembresiaSocio(socioId);
        });

        Assertions.assertEquals("Este socio no tiene abono contratado", exception.getMessage());


        // Verificar que el método save no fue llamado
        Mockito.verify(vencimientoRepository, Mockito.never()).save(Mockito.any(Vencimiento.class));
    }










}
