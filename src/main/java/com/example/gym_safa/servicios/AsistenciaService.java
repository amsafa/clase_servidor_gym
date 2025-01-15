package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.AsistenciaDTO;
import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.AsistenciaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AsistenciaService {


    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private SocioRepository socioRepository;


    /**
     * Buscar todas las asistencias
     *
     * @return
     */

    public List<AsistenciaDTO> getAsistenciasPorSocio(Integer socioId) throws IllegalArgumentException {
        // Verificar si el socio existe
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new IllegalArgumentException("Socio no existe"));

        // Obtener todas las asistencias del socio
        List<Asistencia> asistencias = asistenciaRepository.findBySocioId(socioId);

        if(asistencias.isEmpty()){
            throw new IllegalArgumentException("No hay asistencias para el socio");
        }

        // Convertir las asistencias a DTOs
        List<AsistenciaDTO> asistenciaDTOS = new ArrayList<>();
        for (Asistencia asistencia : asistencias) {
            AsistenciaDTO dto = new AsistenciaDTO();
            dto.setFechaEntrada(asistencia.getFechaEntrada());
            dto.setFechaSalida(asistencia.getFechaSalida());

            dto.setSocioId(socio.getId());
            asistenciaDTOS.add(dto);
        }

        return asistenciaDTOS;
    }
}

    /**
     * Buscar una asistencia por su id
     *
     * @param id
     * @return

    public AsistenciaDTO  getAsistenciaById(Integer id) {
        Asistencia asistencia = asistenciaRepository.findById(id).get();
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setFechaEntrada(asistencia.getFechaEntrada());
        dto.setFechaSalida(asistencia.getFechaSalida());
        dto.setSocioId(asistencia.getSocio().getId());
        return dto;

    }
     */

    /**
     * Crear una asistencia.
     * @param asistenciaDTO
     * @return

public AsistenciaDTO guardarAsistencia(AsistenciaDTO asistenciaDTO) {
    Asistencia asistencia = new Asistencia();
    asistencia.setFechaEntrada(asistenciaDTO.getFechaSalida());
    asistencia.setFechaSalida(asistenciaDTO.getFechaSalida());
    asistencia.setSocio(socioRepository.findById(asistenciaDTO.getSocioId()).orElse(null));
    asistenciaRepository.save(asistencia);
    return asistenciaDTO;
}
     */

    /**
     * Eliminar una asistencia por su id
     * @param id
     * @return

    public void eliminarAsistencia(Integer id) {
        asistenciaRepository.deleteById(id);
    }

     */

    /**
     * Eliminar todas las asistencia del id de un socio
     * @param id
     * @return

    public void eliminarAsistenciaPorSocio(Integer id) {
        List<Asistencia> asistencias = asistenciaRepository.findAll();
        for (Asistencia asistencia : asistencias) {
            if (asistencia.getSocio().getId().equals(id)) {
                asistenciaRepository.deleteById(asistencia.getId());
            }
        }
    }

     */






