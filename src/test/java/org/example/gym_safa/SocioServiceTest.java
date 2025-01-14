package org.example.gym_safa;

import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {SocioService.class})
public class SocioServiceTest {

    @Autowired
    private SocioService service;

    @Test
    public void testGetAllSocios() {
        //GIVEN

        //WHEN
        service.getAllSocios();
    }








}
