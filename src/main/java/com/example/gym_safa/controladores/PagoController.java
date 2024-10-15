package com.example.gym_safa.controladores;

import com.example.gym_safa.dto.PagoDTO;
import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.servicios.PagoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
@AllArgsConstructor
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/id")
    public PagoDTO getPagoById(@RequestParam Integer id) {

        return pagoService.getPagosById(id);

    }

    @GetMapping("/all")
    public List<PagoDTO> getAllPagos() {
        return pagoService.getAllPagos();
    }

    @PostMapping("/guardar")
    public PagoDTO guardarPago(@RequestBody PagoDTO pagoDTO) {
        return pagoService.guardarPago(pagoDTO);
    }

    @DeleteMapping("/eliminar")
    public void eliminarPago(@RequestParam Integer id) {
        pagoService.eliminarPago(id);
    }
}
