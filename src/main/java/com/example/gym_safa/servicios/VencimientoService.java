package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.VencimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor

public class VencimientoService {

    private final SocioService socioService;
    private VencimientoRepository vencimientoRepository;

    /**
     * Buscar todos los vencimientos de las membresias de los usuarios.
     *
     * @param
     * @return
     *
     */
    public List<VencimientoDTO> getVencimientosAll() {
        List<VencimientoDTO> vencimientoDTOS = new ArrayList<>();
        List<Vencimiento> vencimientosMembresias = vencimientoRepository.findAll();
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
        Vencimiento vencimiento = vencimientoRepository.findById(id).get();
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
    Vencimiento vencimiento = vencimientoRepository
            .findById(vencimientoMembresiaDTO.getId())
            .orElse(new Vencimiento());

    vencimiento.setId(vencimientoMembresiaDTO.getId());
    vencimiento.setSocio(vencimientoMembresiaDTO.getSocio());
    vencimiento.setFecha_inicio(vencimientoMembresiaDTO.getFecha_inicio());
    vencimiento.setFecha_fin(vencimientoMembresiaDTO.getFecha_fin());
    vencimiento.setEstado(vencimientoMembresiaDTO.getEstado().equals("ACTIVO") ? Estado.ACTIVO : Estado.INACTIVO);

    vencimientoRepository.save(vencimiento);
    return vencimientoMembresiaDTO;
}

    /**
     * Elimina un vencimiento de membresia por su id
     *
     * @param id
     */
    public void eliminarVencimiento(Integer id) {
        vencimientoRepository.deleteById(id);
    }

    public Vencimiento renovarAbono (Integer idSocio){
        List<Vencimiento> listVencimientoaAntiguo = vencimientoRepository.findBySocioId(idSocio);
        listVencimientoaAntiguo.sort(Comparator.comparing(Vencimiento::getFecha_fin).reversed());

        Vencimiento vencimientoantiguo = listVencimientoaAntiguo.getLast();

        Vencimiento vencimientoNuevo = new Vencimiento();

        if (vencimientoantiguo == null){
            throw new RuntimeException("No se puede renovar el abono porque no existe");
        }

        LocalDate fechaFin = vencimientoantiguo.getFecha_fin();
        LocalDate fechaInicio = vencimientoantiguo.getFecha_inicio();

        Period periodo = Period.between(fechaInicio, fechaFin);

        Integer diferenciaMeses = periodo.getYears() * 12 + periodo.getMonths();

        LocalDate nuevaFechaFin = fechaFin.plusMonths(diferenciaMeses);

        vencimientoNuevo.setFecha_inicio(vencimientoantiguo.getFecha_fin());
        vencimientoNuevo.setFecha_fin(nuevaFechaFin);
        vencimientoNuevo.setEstado(Estado.ACTIVO);
        vencimientoNuevo.setSocio(vencimientoantiguo.getSocio());

        return vencimientoRepository.save(vencimientoNuevo);
    }




}
