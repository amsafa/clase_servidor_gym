package com.example.gym_safa.servicios;

import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.repositorios.MembresiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MembresiaService {

    private MembresiaRepository membresiaRepository;

    /** Metodo que devuelve todas las membresias
     *
     * @param nombre
     * @return
     */
    public List<Membresia> getMembresiaByNombre(NombreMembresia nombre) {

        List<Membresia> membresias = membresiaRepository.findAll();
        return membresias;
    }

    /**
     * Busca una membresia por su id
     *
     * @param id
     * @return
     */
    public Membresia getMembresiasById(Integer id) {
        return membresiaRepository.findById(id).orElse(null);
    }



    /**
     * Guarda una membresia nueva o modifica
     *
     * @param membresia
     * @return
     */
    public Membresia guardarMembresia(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }

    /**
     * Elimina una membresia por su id
     *
     * @param id
     * @return
     */
    public void borrarMembresiaPorId(Integer id) {
        membresiaRepository.deleteById(id);

    }

    /**
     * Elimina todas las membresias
     *
     * @param membresia
     * @return
     */
    public void borrarMembresiaPorNombre(Membresia membresia) {

        membresiaRepository.delete(membresia);
    }



}

