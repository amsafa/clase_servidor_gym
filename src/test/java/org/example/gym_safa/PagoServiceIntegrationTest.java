package org.example.gym_safa;


import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.PagoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PagoServiceIntegrationTest {

    @Mock
    private SocioRepository socioRepository;  // Mock de SocioRepository

    @InjectMocks
    private PagoService pagoService;  // Servicio que estamos probando

    @Test
    void testImporteGastado_Exitoso() {
        // Crear una membresía y vencimiento
        Membresia membresia = new Membresia(100.0);  // Precio ficticio
        Vencimiento vencimiento = new Vencimiento();
        vencimiento.setFecha_inicio(LocalDate.now().minusMonths(2));  // Fecha de inicio hace 2 meses
        vencimiento.setFecha_fin(LocalDate.now().minusMonths(1));     // Fecha de fin hace 1 mes
        vencimiento.setMembresia(membresia);

        // Crear un pago dentro del rango del vencimiento
        Pago pago = new Pago();
        pago.setFechaPago(LocalDate.now().minusMonths(1).plusDays(0));  // Pago dentro del rango

        // Crear un socio con el vencimiento y el pago
        Socio socio = new Socio();
        socio.setId(1);
        socio.setVencimientos(Collections.singletonList(vencimiento));
        socio.setPagos(Collections.singletonList(pago));

        // Simular que el repositorio devuelve el socio con ID 1
        Mockito.when(socioRepository.findById(1)).thenReturn(Optional.of(socio));

        // Llamar al método del servicio
        Double totalGastado = pagoService.importeGastado(1);

        // Verificar que el total gastado es el correcto
        assertEquals(100.0, totalGastado);
    }

    @Test
    void testImporteGastado_SocioNoExiste() {
        // Simular que no se encuentra el socio con el ID
        Integer idSocioInexistente = 999;
        Mockito.when(socioRepository.findById(idSocioInexistente)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción cuando no existe el socio
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(idSocioInexistente);
        });
        assertEquals("El socio con ID 999 no existe en el sistema.", exception.getMessage());
    }

    @Test
    void testImporteGastado_SocioSinMembresias() {
        // Crear un socio sin vencimientos
        Socio socioSinMembresias = new Socio();
        socioSinMembresias.setId(2);
        socioSinMembresias.setVencimientos(Collections.emptyList());

        // Simular que el repositorio devuelve el socio sin vencimientos
        Mockito.when(socioRepository.findById(2)).thenReturn(Optional.of(socioSinMembresias));

        // Verificar que se lanza la excepción cuando no tiene membresías
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(2);
        });
        assertEquals("El socio con ID 2 no tiene membresías registradas.", exception.getMessage());
    }

    @Test
    void testImporteGastado_SocioNoPagoUltimaMembresia() {
        // Crear un socio con vencimientos pero sin pagos
        Membresia membresia = new Membresia(150.0);  // Precio ficticio
        Vencimiento vencimiento = new Vencimiento();
        vencimiento.setFecha_inicio(LocalDate.now().minusMonths(2));
        vencimiento.setFecha_fin(LocalDate.now().minusMonths(1));
        vencimiento.setMembresia(membresia);

        Socio socioSinPago = new Socio();
        socioSinPago.setId(3);
        socioSinPago.setVencimientos(Collections.singletonList(vencimiento));
        socioSinPago.setPagos(Collections.emptyList());  // Sin pagos

        // Simular que el repositorio devuelve el socio sin pagos
        Mockito.when(socioRepository.findById(3)).thenReturn(Optional.of(socioSinPago));

        // Verificar que se lanza la excepción cuando no se ha pagado la última membresía
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.importeGastado(3);
        });
        assertEquals("El socio con ID 3 no ha pagado su última membresía.", exception.getMessage());
    }
}
