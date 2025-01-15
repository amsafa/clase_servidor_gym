/**
package com.example.gym_safa.controladores;

import com.example.gym_safa.dto.AsistenciaDTO;
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


    @GetMapping("/all")
    public List<AsistenciaDTO> getAllAsistencias() {
        return asistenciaService.getAllAsistencias();
    }

    @GetMapping("/id/")
    public AsistenciaDTO getAsistenciaById(@RequestParam Integer id) {
        return asistenciaService.getAsistenciaById(id);
    }


    @PostMapping("/guardar")
    public AsistenciaDTO guardarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
        return asistenciaService.guardarAsistencia(asistenciaDTO);
    }

    @DeleteMapping("/eliminar")
    public void eliminarAsistencia(@RequestParam Integer id) {
        asistenciaService.eliminarAsistencia(id);

}












}
 **/

