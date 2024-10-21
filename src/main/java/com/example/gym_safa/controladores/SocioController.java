package com.example.gym_safa.controladores;

import com.example.gym_safa.dto.AsistenciaResumenDTO;
import com.example.gym_safa.dto.PagoDTO;
import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.servicios.PagoService;
import com.example.gym_safa.servicios.SocioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socio")
@AllArgsConstructor
public class SocioController {

    @Autowired
    private SocioService socioService;
    private PagoService pagoService;

    @GetMapping("/listar")
    public List<SocioDTO> getAllSocios() {
        return socioService.getAllSocios();
    }

    @GetMapping("/id/")
    public SocioDTO getSociosById(@RequestParam Integer id) {
        return socioService.getSociosById(id);
    }

    @PostMapping("/guardar")
    public SocioDTO guardarModificarSocio(@RequestBody SocioDTO socioDTO) {
        return socioService.guardarModificarSocio(socioDTO);
    }

    @DeleteMapping
    public void deleteSocios(@RequestParam Integer id) {
        socioService.deleteSocios(id);
    }

    @GetMapping("/asistencia")
    public AsistenciaResumenDTO getAsistenciaResumenBySocioId(@RequestParam Integer socioId) {
        return socioService.getAsistenciaResumenBySocioId(socioId);
    }


    // EJercicio 3

    @GetMapping("/gasto")
    public Double getPagosBySocioId(@RequestParam Integer socioId) {
        return pagoService.getTotalPagoBySocioId(socioId);
    }
}