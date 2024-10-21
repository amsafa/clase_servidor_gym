package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.MembresiaDTO;
import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.MembresiaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MembresiaService {

    private MembresiaRepository membresiaRepository;

    private SocioRepository socioRepository;

    /** Metodo que devuelve todas las membresias
     *
     *
     * @return
     */
    public List<Membresia> getAllMembresias() {
        return membresiaRepository.findAll();
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
 * Modifica membresia
 *
 * @param membresiaDTO
 * @return
 */
public MembresiaDTO modificarMembresia(MembresiaDTO membresiaDTO) {
    Membresia membresia = membresiaRepository.findById(membresiaDTO.getId()).orElse(null);

    membresia.setDuracionMeses(membresiaDTO.getDuracionMeses());
    membresia.setPrecio(membresiaDTO.getPrecio());

    Membresia savedMembresia = membresiaRepository.save(membresia);

    MembresiaDTO savedMembresiaDTO = new MembresiaDTO();
    savedMembresiaDTO.setId(savedMembresia.getId());
    savedMembresiaDTO.setDuracionMeses(savedMembresia.getDuracionMeses());
    savedMembresiaDTO.setPrecio(savedMembresia.getPrecio());

    return savedMembresiaDTO;
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
    // socio.getMembresia() == new Membresia()





}

