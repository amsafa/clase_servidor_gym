package org.example.gym_safa;

import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
public class PagoServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks //Para inyectar los mocks en la clase a testear
    private PagoService pagoService;

    @BeforeEach
    public void inicializarDatos() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks y la clase a testear antes de cada test
    }

    @Test
    public void testImporteGastadoPositivo() {
        // Given: Preparar el escenario
        Socio socio = new Socio();
        socio.setId(1);

        Membresia membresia1 = new Membresia();
        membresia1.setId(1);
        membresia1.setPrecio(50.0);

        Membresia membresia2 = new Membresia();
        membresia2.setId(2);
        membresia2.setPrecio(100.0);

        Vencimiento vencimiento1 = new Vencimiento();
        vencimiento1.setId(1);
        vencimiento1.setMembresia(membresia1);
        vencimiento1.setFecha_inicio(LocalDate.of(2023, 1, 1));
        vencimiento1.setFecha_fin(LocalDate.of(2023, 1, 31));

        Vencimiento vencimiento2 = new Vencimiento();
        vencimiento2.setId(2);
        vencimiento2.setMembresia(membresia2);
        vencimiento2.setFecha_inicio(LocalDate.of(2023, 2, 1));
        vencimiento2.setFecha_fin(LocalDate.of(2023, 2, 28));

        Pago pago1 = new Pago();
        pago1.setMonto(50.0);
        pago1.setFechaPago(LocalDate.of(2023, 1, 15)); // Pago dentro del rango del vencimiento1

        Pago pago2 = new Pago();
        pago2.setMonto(100.0);
        pago2.setFechaPago(LocalDate.of(2023, 2, 15)); // Pago dentro del rango del vencimiento2

        socio.setVencimientos(Arrays.asList(vencimiento1, vencimiento2));
        socio.setPagos(Arrays.asList(pago1, pago2));

        // Mock del repositorio
        when(socioRepository.findById(1)).thenReturn(Optional.of(socio));

        // When: Ejecutar el método
        Double totalGastado = pagoService.importeGastado(1);

        // Then: Validar el resultado
        assertNotNull(totalGastado, "El total gastado no debe ser nulo.");
        assertEquals(150.0, totalGastado, 0.01, "El total gastado debe ser la suma de los precios de las membresías asociadas.");
        verify(socioRepository, times(1)).findById(1); // Verifica que el repositorio fue llamado una vez
    }

    @Test
    void testImporteGastadoNegativo() {
        // Caso 1: El socio no existe
        Integer idSocioInexistente = 1;
        Mockito.when(socioRepository.findById(idSocioInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(idSocioInexistente);
        });
        assertEquals("El socio con ID 1 no existe en el sistema.", exception.getMessage());

        // Caso 2: El socio no tiene membresías registradas
        Integer idSocioSinMembresias = 2;
        Socio socioSinMembresias = new Socio();
        socioSinMembresias.setId(idSocioSinMembresias);
        socioSinMembresias.setVencimientos(Collections.emptyList());

        Mockito.when(socioRepository.findById(idSocioSinMembresias)).thenReturn(Optional.of(socioSinMembresias));

        exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(idSocioSinMembresias);
        });
        assertEquals("El socio con ID 2 no tiene membresías registradas.", exception.getMessage());

        // Caso 3: El socio no ha pagado su última membresía
        Integer idSocioSinPago = 3;
        Vencimiento vencimiento = new Vencimiento();
        vencimiento.setFecha_inicio(LocalDate.now().minusMonths(2));
        vencimiento.setFecha_fin(LocalDate.now().minusMonths(1));
        vencimiento.setMembresia(new Membresia(50.0));

        Socio socioSinPago = new Socio();
        socioSinPago.setId(idSocioSinPago);
        socioSinPago.setVencimientos(Collections.singletonList(vencimiento));
        socioSinPago.setPagos(Collections.emptyList()); // Sin pagos

        Mockito.when(socioRepository.findById(idSocioSinPago)).thenReturn(Optional.of(socioSinPago));

        exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(idSocioSinPago);
        });
        assertEquals("El socio con ID 3 no ha pagado su última membresía.", exception.getMessage());
        assertNull(exception.getCause()); // No hay causa para la excepción en este caso
    }





}
