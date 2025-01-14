package com.example.gym_safa.controladores;

import com.example.gym_safa.dto.AsistenciaResumenDTO;
import com.example.gym_safa.dto.PagoDTO;
import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.servicios.PagoService;
import com.example.gym_safa.servicios.SocioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




//    @DeleteMapping
//    public void deleteSocios(@RequestParam Integer id) {
//        socioService.deleteSocios(id);
//    }

    // EJercicio 6

//    @DeleteMapping
//    public ResponseEntity<Map<String, String>> deleteSocios(@RequestParam Integer id) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            String message = socioService.deleteSocios(id);
//            response.put("message", message);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            response.put("message", "No se ha podido eliminar el socio");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@DeleteMapping
public ResponseEntity<String> deleteSocios(@RequestParam Integer id) {
    try {
        String message = socioService.deleteSocios(id);
        return ResponseEntity.ok(message);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido eliminar el socio");
    }
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

    // Ejercicio 4, renovar membresía en caso de tener abono activo.

    @PostMapping("/renovar-membresia")
    public ResponseEntity<VencimientoDTO> renovarMembresia(@RequestParam("socioId") Integer socioId) {
        try {
            VencimientoDTO vencimientoDTO = socioService.renovarMembresia(socioId);
            return ResponseEntity.ok(vencimientoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }







}