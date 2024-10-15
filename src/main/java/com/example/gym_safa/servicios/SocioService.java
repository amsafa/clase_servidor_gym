package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.repositorios.SocioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocioService {

    private SocioRepository socioRepository;
    private MembresiaService membresiaService;

    /**
     * Este método devuelve todos los socios
     *
     * @return
     */

    public List<SocioDTO> getAllSocios() {
        List<SocioDTO> socioDTOS = new ArrayList<>();
        List<Socio> socios = socioRepository.findAll();
        for (Socio socio : socios) {
            SocioDTO dto = new SocioDTO();
            dto.setId(socio.getId());
            dto.setNombre(socio.getNombre());
            dto.setDNI(socio.getDNI());
            dto.setFecha_nacimiento(socio.getFecha_nacimiento());
            dto.setCuenta_bancaria(socio.getCuenta_bancaria());
            dto.setTelefono(socio.getTelefono());
            dto.setEmail(socio.getEmail());
            dto.setMembresiaId(socio.getMembresia().getId());
            dto.setFecha_registro(socio.getFecha_registro());
            socioDTOS.add(dto);
        }
        return socioDTOS;
    }

    /**
     * Busca un socio por su id
     *
     * @param id
     * @return
     */

    public SocioDTO getSociosById(Integer id) {
        Socio socio = socioRepository.findById(id).get();
        SocioDTO dto = new SocioDTO();
        dto.setId(socio.getId());
        dto.setNombre(socio.getNombre());
        dto.setDNI(socio.getDNI());
        dto.setFecha_nacimiento(socio.getFecha_nacimiento());
        dto.setCuenta_bancaria(socio.getCuenta_bancaria());
        dto.setTelefono(socio.getTelefono());
        dto.setEmail(socio.getEmail());
        dto.setMembresiaId(socio.getMembresia().getId());
        dto.setFecha_registro(socio.getFecha_registro());
        return dto;

    }

    /**
     * Guarda o modifica un socio
     *
     * @param socios
     * @return
     */

   public SocioDTO guardarModificarSocio(SocioDTO socios) {
    Socio socio = new Socio();
    socio.setId(socios.getId());
    socio.setNombre(socios.getNombre());
    socio.setDNI(socios.getDNI());
    socio.setFecha_nacimiento(socios.getFecha_nacimiento());
    socio.setCuenta_bancaria(socios.getCuenta_bancaria());
    socio.setTelefono(socios.getTelefono());
    socio.setEmail(socios.getEmail());
    socio.setMembresia(socio.getMembresia());
    socio.setFecha_registro(socios.getFecha_registro());
    socioRepository.save(socio);
    return socios;
}


    /**
     * Elimina un socio por su id
     *
     * @param id
     */

    public void deleteSocios(Integer id) {

        socioRepository.deleteById(id);
    }




}
