package com.example.gym_safa.controladores;

import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.servicios.PagoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pago")
@AllArgsConstructor
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/id/")
    public Pago getPagosById(@RequestParam Integer id) {
        Pago pago = pagoService.getPagosById(id);

        return pago;

    }
}
