package org.example.gym_safa;


import com.example.gym_safa.dto.AsistenciaDTO;
import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.AsistenciaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.AsistenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AsistenciaServiceIntegrationTest {

    @Mock
    private SocioRepository socioRepository; //SIMULADO

    @Mock
    private AsistenciaRepository asistenciaRepository; //SIMULADO

    @InjectMocks
    private AsistenciaService asistenciaService; //REAL

    @Test
    void testGetAsistenciasPorSocio() {
        //Configuracion

        Integer socioId = 1;
        Socio socio = new Socio();
        socio.setId(socioId);

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaEntrada(LocalDateTime.of(2021, 1, 1, 10, 0));
        asistencia.setFechaSalida(LocalDateTime.of(2021, 1, 1, 12, 0));

        when (socioRepository.findById(socioId)).thenReturn(Optional.of(socio));
        when (asistenciaRepository.findBySocioId(socioId)).thenReturn(List.of(asistencia));

        //Ejecucion
        List<AsistenciaDTO> resultado = asistenciaService.getAsistenciasPorSocio(socioId);

        // Verificación
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        AsistenciaDTO dto = resultado.get(0);
        assertEquals(asistencia.getFechaEntrada(), dto.getFechaEntrada());
        assertEquals(asistencia.getFechaSalida(), dto.getFechaSalida());
        assertEquals(socioId, dto.getSocioId());
    }

    @Test
    void testGetAsistenciasPorSocio_SocioNoExiste() {
        // Configuración
        Integer socioId = 1;

        when(socioRepository.findById(socioId)).thenReturn(Optional.empty());

        // Ejecución y Verificación
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> asistenciaService.getAsistenciasPorSocio(socioId));

        assertEquals("Socio no existe", excepcion.getMessage());
    }

    @Test
    void testGetAsistenciasPorSocio_SinAsistencias() {
        // Configuración
        Integer socioId = 1;
        Socio socio = new Socio();
        socio.setId(socioId);

        when(socioRepository.findById(socioId)).thenReturn(Optional.of(socio));
        when(asistenciaRepository.findBySocioId(socioId)).thenReturn(new ArrayList<>());

        // Ejecución y Verificación
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> asistenciaService.getAsistenciasPorSocio(socioId));

        assertEquals("No hay asistencias para el socio", excepcion.getMessage());
    }


}
