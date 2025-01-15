package org.example.gym_safa;


import com.example.gym_safa.GymSafaApplication;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.PagoRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.servicios.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GymSafaApplication.class)
@AutoConfigureTestDatabase
public class PagoServiceTest {

    @Autowired
    private PagoService service;

    @Autowired
    private PagoRepository repository;

    @Autowired
    private SocioRepository sociosRepository;

    @BeforeEach
    public void InicializarBaseDatos()  {

        // Crear un socio

        Socio socio1 = new Socio();
        socio1.setNombre("Juan");
        socio1.setEmail("sociodepago@live.com");
        socio1.setTelefono("1234567000");

        socio1.getMembresia().setPrecio(100.0);
        socio1.getMembresia().setDuracionMeses(3);
        socio1.getFecha_registro();


        sociosRepository.save(socio1);
    }


    @Test
    public void TestTotalGastadoPositivo() {
        //
    }




}
