package com.example.gym_safa;

import com.example.gym_safa.modelos.Pago;


import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.servicios.PagoService;
import com.example.gym_safa.servicios.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.enumerados.TipoPago;

import java.util.List;


@SpringBootTest
public class PagoTest {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private SocioService socioService;

    @Test
    void testCrearPago(){
        //Recuperamos un socio de la base de datos
        Socio socio = socioService.getSociosById(1);

        Pago pago = new Pago();
        pago.setSocio(socio);
        pago.setMonto(1000.0);
        pago.setFecha(java.time.LocalDate.now());
        pago.setTipo_pago(TipoPago.TRANSFERENCIA);

        Pago pagoGuardado = pagoService.savePagos(pago);
        System.out.println(pagoGuardado.toString());
    }

    @Test
    void testEditarPago() {
        Pago pago = pagoService.getPagosById(2);
    }

    @Test
    void testEliminarPago() {
        pagoService.deletePagos(2);
    }

    @Test
    void testBuscarPagosPorId() {
        Pago pago = pagoService.getPagosById(1);
        System.out.println(pago.getId());
    }

}
