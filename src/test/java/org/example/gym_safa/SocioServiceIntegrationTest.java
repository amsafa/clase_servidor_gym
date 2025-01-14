package org.example.gym_safa;

import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class SocioServiceIntegrationTest {

    @InjectMocks
    private SocioService service; //REAL

    @Mock
    private SocioRepository repository; //SIMULADO

    @Test
    public void testFindAllIntegracion() {
        //GIVEN: preparacion de datos

        List<SocioDTO> sociosDatos = new ArrayList<>();
        SocioDTO socio1 = new SocioDTO();
        socio1.setId(1);
        socio1.setNombre("Socio1");
        socio1.setDNI("12345678A");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // El formato de fecha que se va a usar
        socio1.setFecha_nacimiento(LocalDate.parse("01/01/1990", formatter));
        socio1.setCuenta_bancaria(1234567890);
        socio1.setTelefono("123456789");
        socio1.setEmail("asdf@live.com");
        socio1.setMembresiaId(1);
        socio1.setFecha_registro(LocalDate.parse("01/01/2021", formatter));
        sociosDatos.add(socio1);


        //WHEN: fase de ejecucion
        List<SocioDTO> socios = service.getAllSocios();


        //THEN : fase de  verificacion
    }

}
