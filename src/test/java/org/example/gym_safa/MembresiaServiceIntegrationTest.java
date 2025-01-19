package org.example.gym_safa;


import com.example.gym_safa.dto.MembresiaDTO;
import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.repositorios.MembresiaRepository;
import com.example.gym_safa.servicios.MembresiaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembresiaServiceIntegrationTest {

    @Mock
    private MembresiaRepository membresiaRepository;

    @InjectMocks
    private MembresiaService membresiaService;

    @Test
    void testGetAllMembresias_Exitoso() {
        // Configuración
        Membresia membresia1 = new Membresia();
        membresia1.setNombre(NombreMembresia.Basico);
        membresia1.setDuracionMeses(12);
        membresia1.setPrecio(100.0);

        Membresia membresia2 = new Membresia();
        membresia2.setNombre(NombreMembresia.Invencible);
        membresia2.setDuracionMeses(6);
        membresia2.setPrecio(200.0);

        when(membresiaRepository.findAll()).thenReturn(List.of(membresia1, membresia2));

        // Ejecución
        List<MembresiaDTO> resultado = membresiaService.getAllMembresias();

        // Verificación
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        MembresiaDTO dto1 = resultado.get(0);
        assertEquals(NombreMembresia.Basico, dto1.getNombre());
        assertEquals(12, dto1.getDuracionMeses());
        assertEquals(100.0, dto1.getPrecio());

        MembresiaDTO dto2 = resultado.get(1);
        assertEquals(NombreMembresia.Invencible, dto2.getNombre());
        assertEquals(6, dto2.getDuracionMeses());
        assertEquals(200.0, dto2.getPrecio());
    }

    @Test
    void testGetAllMembresias_DuracionCero() {
        // Configuración
        Membresia membresia = new Membresia();
        membresia.setNombre(NombreMembresia.Basico);
        membresia.setDuracionMeses(0);
        membresia.setPrecio(100.0);

        when(membresiaRepository.findAll()).thenReturn(List.of(membresia));

        // Ejecución y Verificación
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> membresiaService.getAllMembresias());

        assertEquals("La duracion de la membresia no puede ser 0", excepcion.getMessage());
    }

    @Test
    void testGetAllMembresias_PrecioCero() {
        // Configuración
        Membresia membresia = new Membresia();
        membresia.setNombre(NombreMembresia.Basico);
        membresia.setDuracionMeses(12);
        membresia.setPrecio(0.0);

        when(membresiaRepository.findAll()).thenReturn(List.of(membresia));

        // Ejecución y Verificación
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> membresiaService.getAllMembresias());

        assertEquals("El precio de la membresia no puede ser 0", excepcion.getMessage());
    }

    @Test
    void testGetAllMembresias_SinMembresias() {
        // Configuración
        when(membresiaRepository.findAll()).thenReturn(List.of());

        // Ejecución
        List<MembresiaDTO> resultado = membresiaService.getAllMembresias();

        // Verificación
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }






}
