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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {SocioService.class})
public class SocioServiceTest {

    @Mock
    private VencimientoRepository vencimientoRepository;

    @InjectMocks
    private SocioService socioService;

    @Mock
    private SocioRepository socioRepository;

    @BeforeEach
    public void setUp() {
        // Inicialización de los mocks sin usar @ExtendWith
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
        Mockito.when(vencimientoRepository.findBySocioId(idSocio))
                .thenReturn(vencimientos);

        // WHEN: Se renueva la membresía del socio
        VencimientoDTO resultado = socioService.renovarMembresiaSocio(idSocio);

        // THEN: La membresía debe renovarse correctamente
        Assertions.assertNotNull(resultado, "El resultado no debe ser nulo");
        Assertions.assertEquals(vencimientoAntiguo.getFecha_fin().plusMonths(12), resultado.getFechaFin(),
                "La fecha de fin debe ser 12 meses después del vencimiento anterior");

        // Verificar que el método save fue llamado exactamente una vez
        verify(vencimientoRepository, times(1)).save(Mockito.any(Vencimiento.class));

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
        verify(vencimientoRepository, Mockito.never()).save(Mockito.any(Vencimiento.class));
    }

    @Test
    void TestEditarSocioPositivo () {
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

        // Mock para la validación de que el DNI no está duplicado
        when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.empty());  // No existe el DNI

        // Mock del repositorio para guardar el socio
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

        // When: Se llama al método a probar
        SocioDTO resultado = socioService.guardarModificarSocio(socioDTO);

        // Then: Se verifican los resultados y comportamientos
        assertEquals(socioDTO.getId(), resultado.getId());
        assertEquals(socioDTO.getNombre(), resultado.getNombre());
        assertEquals(socioDTO.getDni(), resultado.getDni());
        assertEquals(socioDTO.getFechaNacimiento(), resultado.getFechaNacimiento());
        assertEquals(socioDTO.getCuentaBancaria(), resultado.getCuentaBancaria());
        assertEquals(socioDTO.getTelefono(), resultado.getTelefono());
        assertEquals(socioDTO.getEmail(), resultado.getEmail());
        assertEquals(socioDTO.getFechaRegistro(), resultado.getFechaRegistro());

        // Verificar que el método save fue llamado exactamente una vez
        verify(socioRepository, times(1)).save(any(Socio.class));
    }

    @Test
    void TestEditarSocioNegativo() {
        // Given: Datos de un socio con un DNI y Email válidos pero duplicados en la base de datos
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setNombre("Juan Perro");
        socioDTO.setCuentaBancaria(1234567890);
        socioDTO.setDni("12345678A");  // DNI válido pero duplicado
        socioDTO.setEmail("juan@correo.com");  // Email válido pero duplicado
        socioDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        socioDTO.setFechaRegistro(LocalDate.of(2021, 1, 1));
        socioDTO.setTelefono("123456789");

        // Simulamos que ya existe un socio con el mismo DNI
        Socio socioExistenteDni = new Socio();
        socioExistenteDni.setId(2);
        socioExistenteDni.setDNI("12345678A");
        when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.of(socioExistenteDni));

        // Simulamos que ya existe un socio con el mismo Email
        Socio socioExistenteEmail = new Socio();
        socioExistenteEmail.setId(3);
        socioExistenteEmail.setEmail("juan@correo.com");
        when(socioRepository.findByEmail(socioDTO.getEmail())).thenReturn(Optional.of(socioExistenteEmail));

        // When: Se lanza una excepción porque el DNI ya está registrado
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            socioService.guardarModificarSocio(socioDTO);
        });

        // Then: Se verifica que el mensaje de error sea el esperado para el DNI duplicado
        Assertions.assertEquals("El DNI ya está registrado en otro socio", exception.getMessage());

        // Verificar que el método save no fue llamado
        verify(socioRepository, never()).save(any(Socio.class));

        // Cambiar el escenario: ahora probamos el caso del Email duplicado sin DNI duplicado
        socioDTO.setDni("98765432B"); // Asignamos un DNI diferente
        socioDTO.setEmail("juan@correo.com"); // Email duplicado

        // When: Se lanza una excepción porque el Email ya está registrado
        exception = Assertions.assertThrows(RuntimeException.class, () -> {
            socioService.guardarModificarSocio(socioDTO);
        });

        // Then: Se verifica que el mensaje de error sea el esperado para el Email duplicado
        Assertions.assertEquals("El email ya está registrado en otro socio", exception.getMessage());

        // Verificar que el método save no fue llamado
        verify(socioRepository, never()).save(any(Socio.class));
    }

    @Test
    void TestEliminarUsuarioPositivo() {
        // Given: Datos de prueba
        Integer socioId = 1;
        Socio socio = new Socio();
        socio.setId(socioId);

        // when: Configuración de mocks
        when(socioRepository.findById(socioId)).thenReturn(java.util.Optional.of(socio));
        doNothing().when(socioRepository).deleteById(socioId);

        // Llamada al método
        String result = socioService.deleteSocios(socioId);

        // Then: Verificaciones
        assertEquals("Socio eliminado", result);
        verify(socioRepository, times(1)).findById(socioId);
        verify(socioRepository, times(1)).deleteById(socioId);
    }

    @Test
    void TestEliminarUsuarioNegativo() {
        // Given: Un socio ID inexistente
        Integer socioId = 1;
        when(socioRepository.findById(socioId)).thenReturn(java.util.Optional.empty());

        // When: Se intenta eliminar un socio con ID inexistente
        SocioService.SocioNotFoundException exception = assertThrows(SocioService.SocioNotFoundException.class, () -> {
            socioService.deleteSocios(socioId);
        });

        // Then: Se lanza una excepción con el mensaje esperado
        assertEquals("Socio no encontrado.", exception.getMessage());
        verify(socioRepository, times(1)).findById(socioId);
        verify(socioRepository, never()).deleteById(socioId);
    }
}
