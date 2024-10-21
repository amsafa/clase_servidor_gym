package com.example.gym_safa.controladores;

import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.servicios.VencimientoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/vencimiento")

public class VencimientoController {

    @Autowired
    private VencimientoService vencimientoSevice;

    @GetMapping("/listar")
    public List<VencimientoDTO> getTodosVencimientos() {
        return vencimientoSevice.getVencimientosAll();
    }

    @GetMapping("/id")
    public VencimientoDTO getVencimientoById(@RequestParam Integer id) {
        return vencimientoSevice.getVencimientoById(id);
    }

    @PostMapping("/editar")
    public VencimientoDTO editarVencimiento(@RequestBody VencimientoDTO vencimientoDTO) {
        return vencimientoSevice.modificarVencimiento(vencimientoDTO);
    }

    @DeleteMapping("/eliminar")
    public void eliminarVencimientoId(@RequestParam Integer id) {
        vencimientoSevice.eliminarVencimiento(id);
    }

}
