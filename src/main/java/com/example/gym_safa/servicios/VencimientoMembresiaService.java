package com.example.gym_safa.servicios;

import com.example.gym_safa.modelos.VencimientoMembresia;
import com.example.gym_safa.repositorios.VencimientoMembresiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class VencimientoMembresiaService {

    private VencimientoMembresiaRepository vencimientosMembresiaRepository;

    /**
     * Buscar todos los vencimientos de membresias.
     *
     * @param
     * @return
     *
     */
    public List<VencimientoMembresia> getAllVencimientosMembresias() {
        return vencimientosMembresiaRepository.findAll();
    }

    /**
     * Buscar un vencimiento de membresia por su id
     *
     * @param id
     * @return
     */

    public VencimientoMembresia getVencimientosMembresiasById(Integer id) {
        return vencimientosMembresiaRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un vencimiento de membresia nuevo o modifica
     *
     * @param vencimientoMembresia
     * @return
     */
    public VencimientoMembresia saveVencimientosMembresias(VencimientoMembresia vencimientoMembresia) {
        return vencimientosMembresiaRepository.save(vencimientoMembresia);
    }

    /**
     * Elimina un vencimiento de membresia por su id
     *
     * @param id
     */
    public void deleteVencimientosMembresias(Integer id) {
        vencimientosMembresiaRepository.deleteById(id);
    }




}
