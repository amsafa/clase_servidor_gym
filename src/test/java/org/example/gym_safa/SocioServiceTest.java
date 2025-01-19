package org.example.gym_safa;

import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.repositorios.VencimientoRepository;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
public class SocioServiceTest {

    @Autowired
    private SocioService socioService;

    @MockBean // Mock del repositorio de vencimientos para simular la base de datos
    private VencimientoRepository vencimientoRepository;

    @MockBean
    private SocioRepository socioRepository;

    @BeforeEach
    public void inicializacionDatos() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks manualmente
    }

    @Test
    public void testRenovarAbonoPositivo() {
        // GIVEN: Un socio con un vencimiento activo
        Integer idSocio = 1;

        Vencimiento vencimientoAntiguo = new Vencimiento();
        vencimientoAntiguo.setId(1);
        vencimientoAntiguo.setFecha_inicio(LocalDate.of(2023, 1, 1));
        vencimientoAntiguo.setFecha_fin(LocalDate.of(2024, 1, 31));
        vencimientoAntiguo.setEstado(Estado.ACTIVO);

        List<Vencimiento> vencimientos = new ArrayList<>();
        vencimientos.add(vencimientoAntiguo);

        // Configurar el mock del repositorio
        when(vencimientoRepository.findBySocioId(idSocio))
                .thenReturn(vencimientos);

        // WHEN: Se renueva la membresía del socio
        VencimientoDTO resultado = socioService.renovarMembresiaSocio(idSocio);

        // THEN: La membresía debe renovarse correctamente
        Assertions.assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(vencimientoAntiguo.getFecha_fin().plusMonths(12), resultado.getFechaFin(),
                "La fecha de fin debe ser 12 meses después del vencimiento anterior");

        // Verificar que el método save fue llamado exactamente una vez
        verify(vencimientoRepository, times(1)).save(Mockito.any(Vencimiento.class));

        // Verificar que el mensaje de renovación exitosa esté presente
        assertEquals("Renovación exitosa", resultado.getMensaje(),
                "El mensaje debe ser 'Renovación exitosa'");
    }


    @Test
    void testRenovarAbonoNegativo() {
        // GIVEN: Un socio sin membresía activa
        Integer socioId = 1;

        // Configurar el mock para que devuelva una lista vacía
        when(vencimientoRepository.findBySocioId(socioId)).thenReturn(Collections.emptyList());

        // WHEN & THEN: Se lanza una excepción al intentar renovar la membresía
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            socioService.renovarMembresiaSocio(socioId);
        });

        assertEquals("Este socio no tiene abono contratado", exception.getMessage());

        // Verificar que el método save no fue llamado
        verify(vencimientoRepository, never()).save(Mockito.any(Vencimiento.class));
    }

    @Test
    void TestEditarSocioPositivo() {
        // Given: Datos de un socio válidos
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setNombre("Juan Perro");
        socioDTO.setCuentaBancaria(1234567890);
        socioDTO.setDni("12345678A");
        socioDTO.setEmail("juan_probando@live.com");
        socioDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        socioDTO.setFechaRegistro(LocalDate.of(2021, 1, 1));
        socioDTO.setTelefono("123456789");

        when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.empty());

        Socio socio = new Socio();
        socio.setId(socioDTO.getId());
        socio.setNombre(socioDTO.getNombre());
        socio.setDNI(socioDTO.getDni());
        socio.setFecha_nacimiento(socioDTO.getFechaNacimiento());
        socio.setCuenta_bancaria(socioDTO.getCuentaBancaria());
        socio.setTelefono(socioDTO.getTelefono());
        socio.setEmail(socioDTO.getEmail());
        socio.setFecha_registro(socioDTO.getFechaRegistro());

        when(socioRepository.save(any(Socio.class))).thenReturn(socio);

        SocioDTO resultado = socioService.guardarModificarSocio(socioDTO);

        assertEquals(socioDTO.getId(), resultado.getId());
        assertEquals(socioDTO.getNombre(), resultado.getNombre());
        assertEquals(socioDTO.getDni(), resultado.getDni());
        assertEquals(socioDTO.getFechaNacimiento(), resultado.getFechaNacimiento());
        assertEquals(socioDTO.getCuentaBancaria(), resultado.getCuentaBancaria());
        assertEquals(socioDTO.getTelefono(), resultado.getTelefono());
        assertEquals(socioDTO.getEmail(), resultado.getEmail());
        assertEquals(socioDTO.getFechaRegistro(), resultado.getFechaRegistro());

        verify(socioRepository, times(1)).save(any(Socio.class));
    }

    @Test
    void TestEditarSocioNegativo() {
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setNombre("Juan Perro");
        socioDTO.setCuentaBancaria(1234567890);
        socioDTO.setDni("12345678A");
        socioDTO.setEmail("juan@correo.com");
        socioDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        socioDTO.setFechaRegistro(LocalDate.of(2021, 1, 1));
        socioDTO.setTelefono("123456789");

        Socio socioExistenteDni = new Socio();
        socioExistenteDni.setId(2);
        socioExistenteDni.setDNI("12345678A");
        when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.of(socioExistenteDni));

        Socio socioExistenteEmail = new Socio();
        socioExistenteEmail.setId(3);
        socioExistenteEmail.setEmail("juan@correo.com");
        when(socioRepository.findByEmail(socioDTO.getEmail())).thenReturn(Optional.of(socioExistenteEmail));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            socioService.guardarModificarSocio(socioDTO);
        });
        assertEquals("El DNI ya está registrado en otro socio", exception.getMessage());
        verify(socioRepository, never()).save(any(Socio.class));

        socioDTO.setDni("98765432B");
        exception = assertThrows(RuntimeException.class, () -> {
            socioService.guardarModificarSocio(socioDTO);
        });
        assertEquals("El email ya está registrado en otro socio", exception.getMessage());
        verify(socioRepository, never()).save(any(Socio.class));
    }

    @Test
    void TestEliminarUsuarioPositivo() {
        Integer socioId = 1;
        Socio socio = new Socio();
        socio.setId(socioId);

        when(socioRepository.findById(socioId)).thenReturn(Optional.of(socio));
        doNothing().when(socioRepository).deleteById(socioId);

        String result = socioService.deleteSocios(socioId);

        assertEquals("Socio eliminado", result);
        verify(socioRepository, times(1)).findById(socioId);
        verify(socioRepository, times(1)).deleteById(socioId);
    }

    @Test
    void TestEliminarUsuarioNegativo() {
        Integer socioId = 1;
        when(socioRepository.findById(socioId)).thenReturn(Optional.empty());

        SocioService.SocioNotFoundException exception = assertThrows(SocioService.SocioNotFoundException.class, () -> {
            socioService.deleteSocios(socioId);
        });

        assertEquals("Socio no encontrado.", exception.getMessage());
        verify(socioRepository, times(1)).findById(socioId);
        verify(socioRepository, never()).deleteById(socioId);
    }
}

