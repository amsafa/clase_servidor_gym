package com.example.gym_safa.servicios;

import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.enumerados.DiaSemana;
import com.example.gym_safa.repositorios.AsistenciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor

public class AsistenciaService {


    //@Autowired
    private AsistenciaRepository asistenciaRepository;




    /**
     * Busca una asistencia por su id
     *
     * @param id
     * @return
     */

    public Asistencia getAsistenciasById(Integer id) {

        return asistenciaRepository.findById(id).orElse(null);
    }

    /**
     * Guarda una asistencia nueva o modifica
     *
     * @param asistencia
     * @return
     */
    public Asistencia saveAsistencias(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    /**
     * Elimina una asistencia por su id
     *
     * @param id
     */
    public void deleteAsistencias(Integer id) {
        asistenciaRepository.deleteById(id);
    }

    /**
     * Elimina todas las asistencias
     *
     * @param asistencia
     *
     * @return
     */

    public void deleteAllAsistencias(Asistencia asistencia) {
        asistenciaRepository.delete(asistencia);
    }


}

