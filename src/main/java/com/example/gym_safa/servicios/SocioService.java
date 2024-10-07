package com.example.gym_safa.servicios;

import com.example.gym_safa.repositorios.SocioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gym_safa.modelos.Socio;

import java.util.List;

@Service
@AllArgsConstructor
public class SocioService {

    private SocioRepository socioRepository;

    /**
     * Este método devuelve todos los socios
     *
     * @return
     */

    public List<Socio> getAllSocios() {

        return socioRepository.findAll();
    }

    /**
     * Busca un socio por su id
     *
     * @param id
     * @return
     */

    public Socio getSociosById(Integer id) {

        return socioRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un socio nuevo o modifica
     *
     * @param socios
     * @return
     */

    public Socio saveSocios(Socio socios) {

        return socioRepository.save(socios);

    }

    /**
     * Elimina un socio por su id
     *
     * @param id
     */

    public void deleteSocios(Integer id) {

        socioRepository.deleteById(id);
    }

    /**
     * Este método extrae todos los socios
     * @return
     *
     */

    public List<Socio> getAll() {
        return socioRepository.findAll();
    }


}
