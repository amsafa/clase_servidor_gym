package com.example.gym_safa.controladores;


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
    public List<Socio> getAllSocios() {
        List<Socio> socios = socioService.getAllSocios();
        return socios;
    }

    // Esta es una forma de hacer extraer el id de la url

    @GetMapping("/id/")
    public Socio getSociosById(@RequestParam Integer id) {
        Socio socio = socioService.getSociosById(id);
        return socio;
    }

    // Esta es otra forma de hacer extraer el id de la url
    @GetMapping("/id/{id}")
    public Socio getSociosByPath(@PathVariable Integer id) {
        Socio socio = socioService.getSociosById(id);
        return socio;
    }

    @PostMapping
    public Socio saveSocios( @RequestBody Socio socios) {
        Socio socio = socioService.saveSocios(socios);
        return socio;
    }

    @DeleteMapping
    public void deleteSocios(@RequestParam Integer id) {
        socioService.deleteSocios(id);
    }


}
