package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.VencimientoMembresiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class VencimientoMembresiaService {

    private VencimientoMembresiaRepository vencimientosMembresiaRepository;

    /**
     * Buscar todos los vencimientos de las membresias de los usuarios.
     *
     * @param
     * @return
     *
     */
    public List<VencimientoDTO> getVencimientosAll() {
        List<VencimientoDTO> vencimientoDTOS = new ArrayList<>();
        List<Vencimiento> vencimientosMembresias = vencimientosMembresiaRepository.findAll();
        for (Vencimiento vencimiento : vencimientosMembresias) {
            VencimientoDTO dto = new VencimientoDTO();
            dto.setId(vencimiento.getId());
            dto.setSocio(vencimiento.getSocio());
            dto.setFecha_inicio(vencimiento.getFecha_inicio());
            dto.setFecha_fin(vencimiento.getFecha_fin());
            dto.setEstado(vencimiento.getEstado().name());
            vencimientoDTOS.add(dto);
        }

        return vencimientoDTOS;


    }
    /**
     * Buscar un vencimiento de membresia por su id
     *
     * @param id
     * @return
     *
     *
     *
     */

    public VencimientoDTO getVencimientoById(Integer id) {
        Vencimiento vencimiento = vencimientosMembresiaRepository.findById(id).get();
        VencimientoDTO dto = new VencimientoDTO();
        dto.setId(vencimiento.getId());
        dto.setSocio(vencimiento.getSocio());
        dto.setFecha_inicio(vencimiento.getFecha_inicio());
        dto.setFecha_fin(vencimiento.getFecha_fin());
        dto.setEstado(vencimiento.getEstado().name());
        return dto;
    }

    /**
     * Guarda un vencimiento de membresia nuevo o modifica
     *
     * @param vencimientoMembresiaDTO
     * @return
     */
    public VencimientoDTO modificarVencimiento(VencimientoDTO vencimientoMembresiaDTO) {
    Vencimiento vencimiento = vencimientosMembresiaRepository
            .findById(vencimientoMembresiaDTO.getId())
            .orElse(new Vencimiento());

    vencimiento.setId(vencimientoMembresiaDTO.getId());
    vencimiento.setSocio(vencimientoMembresiaDTO.getSocio());
    vencimiento.setFecha_inicio(vencimientoMembresiaDTO.getFecha_inicio());
    vencimiento.setFecha_fin(vencimientoMembresiaDTO.getFecha_fin());
    vencimiento.setEstado(vencimientoMembresiaDTO.getEstado().equals("ACTIVO") ? Estado.ACTIVO : Estado.INACTIVO);

    vencimientosMembresiaRepository.save(vencimiento);
    return vencimientoMembresiaDTO;
}

    /**
     * Elimina un vencimiento de membresia por su id
     *
     * @param id
     */
    public void eliminarVencimiento(Integer id) {
        vencimientosMembresiaRepository.deleteById(id);
    }




}
