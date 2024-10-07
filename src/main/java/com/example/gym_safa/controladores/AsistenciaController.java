package com.example.gym_safa.controladores;

import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.servicios.AsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asistencia")
@AllArgsConstructor
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping()
    public Asistencia getAsistenciaById(@RequestParam Integer id) {
        Asistencia asistencia = asistenciaService.getAsistenciaById(id);
        return asistencia;
    }

    @GetMapping("/id/{id}")
    public Asistencia getAsistenciaByPath(@PathVariable Integer id) {
        Asistencia asistencia = asistenciaService.getAsistenciaById(id);
        return asistencia;
    }

    @PostMapping()
    public Asistencia guardarAsistencia(@RequestBody Asistencia asistencia) {
        Asistencia asistenciaGuardada = asistenciaService.guardarAsistencia(asistencia);
        return asistenciaGuardada;


    }

    @DeleteMapping()
    public void borrarAsistenciaPorId(@PathVariable Integer id) {
        asistenciaService.borrarAsistenciaPorId(id);
    }


}

