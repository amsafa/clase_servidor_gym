package com.example.gym_safa.controladores;


import com.example.gym_safa.dto.AsistenciaResumenDTO;
import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.modelos.Socio;
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

    @GetMapping("/listar")
    public List<SocioDTO> getAllSocios() {
        return socioService.getAllSocios();
    }

    // Esta es una forma de hacer extraer el id de la url

   @GetMapping("/id/")
    public SocioDTO getSociosById(@RequestParam Integer id) {
        return socioService.getSociosById(id);
}

    // Esta es otra forma de hacer extraer el id de la url
//    @GetMapping("/id/{id}")
//    public Socio getSociosByPath(@PathVariable Integer id) {
//        Socio socio = socioService.getSociosById(id);
//        return socio;
//    }

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






}
