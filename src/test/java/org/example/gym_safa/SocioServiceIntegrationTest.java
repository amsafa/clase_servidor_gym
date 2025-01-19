package org.example.gym_safa;

import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.repositorios.VencimientoRepository;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class SocioServiceIntegrationTest {

    @InjectMocks //Inyecta el mock en la clase a testear (SocioService) y se encarga de inyectar los mocks que se han anotado con @Mock en esta clase
    private SocioService service; //REAL

    @Mock
    private SocioRepository socioRepository; //SIMULADO

    @Mock
    private VencimientoRepository vencimientoRepository; // SIMULADO

    @Test
    public void testFindAllIntegracionPrueba() {
        //GIVEN: preparacion de datos

        List<SocioDTO> sociosDatos = new ArrayList<>();
        SocioDTO socio1 = new SocioDTO();
        socio1.setId(1);
        socio1.setNombre("Socio1");
        socio1.setDni("12345678A");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // El formato de fecha que se va a usar
        socio1.setFechaNacimiento(LocalDate.parse("01/01/1990", formatter));
        socio1.setCuentaBancaria(1234567890);
        socio1.setTelefono("123456789");
        socio1.setEmail("asdf@live.com");
        socio1.setFechaRegistro(LocalDate.parse("01/01/2021", formatter));
        sociosDatos.add(socio1);


        //WHEN: fase de ejecucion
        List<SocioDTO> socios = service.getAllSocios();


        //THEN : fase de  verificacion

    }


    // Caso 4
    @Test
    public void testRenovarMembresiaSocioIntegracion() {
        // GIVEN
        Integer socioId = 1;
        Vencimiento vencimientoAntiguo = new Vencimiento();
        vencimientoAntiguo.setId(1);
        vencimientoAntiguo.setFecha_inicio(LocalDate.of(2023, 1, 1));
        vencimientoAntiguo.setFecha_fin(LocalDate.of(2024, 1, 31));

        List<Vencimiento> vencimientos = new ArrayList<>();
        vencimientos.add(vencimientoAntiguo);

        Mockito.when(vencimientoRepository.findBySocioId(socioId)).thenReturn(vencimientos);

        // WHEN
        VencimientoDTO resultado = service.renovarMembresiaSocio(socioId);

        // THEN
        assertNotNull(resultado);
        assertEquals(vencimientoAntiguo.getFecha_fin().plusMonths(12), resultado.getFechaFin());
        assertEquals("Renovación exitosa", resultado.getMensaje());
        Mockito.verify(vencimientoRepository, Mockito.times(1)).save(Mockito.any(Vencimiento.class));
    }

    @Test
    public void testRenovarMembresiaSocioIntegracionSinAbono() {
        // GIVEN
        Integer socioId = 1;
        Mockito.when(vencimientoRepository.findBySocioId(socioId)).thenReturn(new ArrayList<>());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> service.renovarMembresiaSocio(socioId));
        Mockito.verify(vencimientoRepository, Mockito.times(0)).save(Mockito.any(Vencimiento.class));
    }

    // Caso 5
    @Test
    public void testGuardarModificarSocioIntegracion() {
        // GIVEN
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setNombre("Juan Perro");
        socioDTO.setCuentaBancaria(1234567890);
        socioDTO.setDni("12345678A");
        socioDTO.setEmail("juan_probando@live.com");
        socioDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        socioDTO.setFechaRegistro(LocalDate.of(2021, 1, 1));
        socioDTO.setTelefono("123456789");


        Mockito.when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.empty());
        Mockito.when(socioRepository.findByEmail(socioDTO.getEmail())).thenReturn(Optional.empty());

        Socio socio = new Socio();
        socio.setId(socioDTO.getId());
        socio.setNombre(socioDTO.getNombre());
        socio.setDNI(socioDTO.getDni());
        socio.setFecha_nacimiento(socioDTO.getFechaNacimiento());
        socio.setCuenta_bancaria(socioDTO.getCuentaBancaria());
        socio.setTelefono(socioDTO.getTelefono());
        socio.setEmail(socioDTO.getEmail());
        socio.setFecha_registro(socioDTO.getFechaRegistro());

        Mockito.when(socioRepository.save(Mockito.any(Socio.class))).thenReturn(socio);

        // WHEN
        SocioDTO resultado = service.guardarModificarSocio(socioDTO);

        // THEN
        assertEquals(socioDTO.getId(), resultado.getId());
        assertEquals(socioDTO.getNombre(), resultado.getNombre());
        Mockito.verify(socioRepository, Mockito.times(1)).save(Mockito.any(Socio.class));
    }

    @Test
    public void testGuardarModificarSocioIntegracionDNIExistente() {
        // GIVEN
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(1);
        socioDTO.setDni("12345678A");

        Socio socioExistente = new Socio();
        socioExistente.setDNI("12345678A");

        // Usar lenient para permitir que este stub no sea utilizado
        Mockito.lenient().when(socioRepository.findByDNI(socioDTO.getDni())).thenReturn(Optional.of(socioExistente));

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> service.guardarModificarSocio(socioDTO));
        Mockito.verify(socioRepository, Mockito.times(0)).save(Mockito.any(Socio.class));
    }



    //Caso 6
    @Test
    public void testEliminarSocioIntegracion() {
        // GIVEN
        Integer socioId = 1;
        Socio socio = new Socio();
        socio.setId(socioId);

        Mockito.when(socioRepository.findById(socioId)).thenReturn(Optional.of(socio));
        Mockito.doNothing().when(socioRepository).deleteById(socioId);

        // WHEN
        String result = service.deleteSocios(socioId);

        // THEN
        assertEquals("Socio eliminado", result);
        Mockito.verify(socioRepository, Mockito.times(1)).findById(socioId);
        Mockito.verify(socioRepository, Mockito.times(1)).deleteById(socioId);
    }

    @Test
    public void testEliminarSocioIntegracionNoEncontrado() {
        // GIVEN
        Integer socioId = 1;
        Mockito.when(socioRepository.findById(socioId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(SocioService.SocioNotFoundException.class, () -> service.deleteSocios(socioId));
        Mockito.verify(socioRepository, Mockito.times(1)).findById(socioId);
        Mockito.verify(socioRepository, Mockito.times(0)).deleteById(socioId);
    }














}
