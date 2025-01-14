package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.MembresiaDTO;
import com.example.gym_safa.enumerados.NombreMembresia;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.MembresiaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class MembresiaService {

    private MembresiaRepository membresiaRepository;

    private SocioRepository socioRepository;

    /** Metodo que devuelve todas las membresias
     *
     *
     * @return
     */


    public List<MembresiaDTO> getAllMembresias() {
        List<Membresia> membresias = membresiaRepository.findAll();
        List<MembresiaDTO> membresiaDTOS = new ArrayList<>();



        for(Membresia m: membresias){

            if(m.getDuracionMeses() == 0){
                throw new IllegalArgumentException("La duracion de la membresia no puede ser 0");
            }
            if(m.getPrecio() == 0){
                throw new IllegalArgumentException("El precio de la membresia no puede ser 0");
            }
            MembresiaDTO membresiaDTO = new MembresiaDTO();
            membresiaDTO.setNombre(NombreMembresia.valueOf(m.getNombre().name()));
            membresiaDTO.setPrecio(m.getPrecio());
            membresiaDTO.setDuracionMeses(m.getDuracionMeses());
            membresiaDTOS.add(membresiaDTO);
        }





        return membresiaDTOS;
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

