package com.example.gym_safa.controladores;


import com.example.gym_safa.dto.AsistenciaDTO;
import com.example.gym_safa.dto.MembresiaDTO;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.servicios.MembresiaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/membresia")

public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    @GetMapping("/bonos")
    public List<Membresia> getAllMembresias() {
        List<Membresia> membresias = membresiaService.getAllMembresias();
        return membresias;
    }

    @GetMapping("/id")
    public Membresia getMembresiasById(@RequestParam Integer id) {

        return membresiaService.getMembresiasById(id);
    }



    @PostMapping("/modificar")
    public MembresiaDTO modificarMembresia(@RequestBody MembresiaDTO membresiaDTO) {
        return membresiaService.modificarMembresia(membresiaDTO);
    }





}
