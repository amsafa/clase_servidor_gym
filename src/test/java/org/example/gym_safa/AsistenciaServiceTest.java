package org.example.gym_safa;

import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.dto.AsistenciaDTO;
import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.AsistenciaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.AsistenciaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
@Transactional
public class AsistenciaServiceTest {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private SocioRepository socioRepository;

    @BeforeEach
    public void inicializarDatos() {

        //Limpiar las tablas relacionadas
        asistenciaRepository.deleteAll();
        socioRepository.deleteAll();

        // Crea un socio para asociar a las asistencias y guárdalo en la base de datos
        Socio socio1 = new Socio();
        socio1.setNombre("Pepe");
        socio1.setDNI("12345678A");
        socio1.setFecha_nacimiento(LocalDate.of(1990, 1, 1));
        socio1.setCuenta_bancaria(123456789);
        socio1.setTelefono("123456789");
        socio1.setFecha_registro(LocalDate.of(2023, 12, 1));
        socio1.setEmail("pepe.prueba@live.com");
        socioRepository.save(socio1);

        // Crear un socio sin asistencia
        Socio socio2 = new Socio();
        socio2.setNombre("Juan");
        socio2.setDNI("12345678B");
        socio2.setFecha_nacimiento(LocalDate.of(1990, 1, 1));
        socio2.setCuenta_bancaria(123406789);
        socio2.setTelefono("123456009");
        socio2.setFecha_registro(LocalDate.of(2023, 12, 2));
        socio2.setEmail("juan.sinasistencia@live.com");
        socioRepository.save(socio2);

        // Crea asistencias asociadas al socio persistido
        Asistencia asistencia1 = new Asistencia();
        asistencia1.setFechaEntrada(LocalDateTime.of(2023, 12, 1, 9, 0));
        asistencia1.setFechaSalida(LocalDateTime.of(2023, 12, 1, 11, 0));
        asistencia1.setSocio(socio1); // Asigna el socio persistido
        asistenciaRepository.save(asistencia1);

        Asistencia asistencia2 = new Asistencia();
        asistencia2.setFechaEntrada(LocalDateTime.of(2023, 12, 2, 10, 0));
        asistencia2.setFechaSalida(LocalDateTime.of(2023, 12, 2, 12, 0));
        asistencia2.setSocio(socio1); // Asigna el socio persistido
        asistenciaRepository.save(asistencia2);



    }

    @Test
    public void TestTotalAsistenciaPositivo() {

        //GIVEN: Preparamos los datos de prueba
        Socio socio = socioRepository.findAll().get(0);

        // WHEN: Llamamos al método del servicio que queremos probar
        List<AsistenciaDTO> asistenciaDTOS = asistenciaService.getAsistenciasPorSocio(socio.getId());

        // THEN: Comprobamos que el resultado es el esperado
        assertEquals(2, asistenciaDTOS.size());
        assertEquals(LocalDateTime.of(2023, 12, 1, 9, 0), asistenciaDTOS.get(0).getFechaEntrada());
        assertEquals(LocalDateTime.of(2023, 12, 2, 10, 0), asistenciaDTOS.get(1).getFechaEntrada());

    }

    @Test
    public void testTotalAsistenciaNegativo() {


        // Caso: Usuario sin asistencias
        // GIVEN: Un socio existente pero sin asistencias
        Socio socio2 = socioRepository.findAll().get(1);

        // WHEN: Se consulta el historial de asistencias del socio
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            asistenciaService.getAsistenciasPorSocio(socio2.getId());
        });

        // THEN: Se lanza una excepción con el mensaje esperado
        assertEquals("No hay asistencias para el socio", exception1.getMessage());

        // Caso: Usuario no existente
        // GIVEN: Un ID de usuario inexistente
        Integer usuarioNoExistenteId = 999;

        // WHEN: Se consulta el historial de asistencias para un usuario inexistente
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            asistenciaService.getAsistenciasPorSocio(usuarioNoExistenteId);
        });

        // THEN: Se lanza una excepción con el mensaje esperado
        assertEquals("Socio no existe", exception2.getMessage());
    }





}