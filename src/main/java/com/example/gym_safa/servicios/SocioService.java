package com.example.gym_safa.servicios;


import com.example.gym_safa.dto.AsistenciaResumenDTO;
import com.example.gym_safa.dto.SocioDTO;
import com.example.gym_safa.dto.VencimientoDTO;
import com.example.gym_safa.enumerados.Estado;
import com.example.gym_safa.modelos.Asistencia;
import com.example.gym_safa.modelos.Membresia;
import com.example.gym_safa.modelos.Socio;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.AsistenciaRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import com.example.gym_safa.repositorios.VencimientoRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.time.Duration;

@Service
@AllArgsConstructor
public class SocioService {

    private SocioRepository socioRepository;
    private MembresiaService membresiaService;
    private AsistenciaRepository asistenciaRepository;
    private VencimientoRepository vencimientoRepository;

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

        if (socio.getMembresia() == null) {
            throw new RuntimeException("No se puede mostrar los datos porque falta la membresía por contratar");
        }
        dto.setMembresiaId(socio.getMembresia().getId());
        dto.setFecha_registro(socio.getFecha_registro());


        return dto;

    }
    public Socio getSocioById(Integer id) {
        return socioRepository.findById(id).orElse(null);
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

    //Ejercicio 6

    public String deleteSocios(Integer id) {
        try {
            Socio socio = socioRepository.findById(id).orElseThrow(() -> new RuntimeException("Socio no encontrado."));
            if (socio.getMembresia() == null) {
                throw new RuntimeException("No se ha podido eliminar el socio, falta la membresia.");
            }
            socioRepository.deleteById(id);
            return "Socio eliminado";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Este método devuelve toda la asistencia del socio
     *
     * @return
     */

    //EJercicio 2
    public AsistenciaResumenDTO getAsistenciaResumenBySocioId(Integer socioId) {
        List<Asistencia> asistencias = asistenciaRepository.findBySocioId(socioId);
        int totalDias = asistencias.size();
        long totalHoras = asistencias.stream()
                .mapToLong(a -> Duration.between(a.getFechaEntrada(), a.getFechaSalida()).toHours())
                .sum();
        return new AsistenciaResumenDTO(socioId, totalDias, totalHoras);
    }


    /**
     * Este método devuelve los vencimientos de un socio
     *
     * @param socioId
     */



    public VencimientoDTO renovarMembresia(Integer socioId) {
        List<Vencimiento> listVencimientoaAntiguo = vencimientoRepository.findBySocioId(socioId);
        listVencimientoaAntiguo.sort(Comparator.comparing(Vencimiento::getFecha_fin).reversed());

        Vencimiento vencimientoantiguo = listVencimientoaAntiguo.get(0);

        if (vencimientoantiguo == null) {
            throw new RuntimeException("No se puede renovar el abono porque no existe");
        }

        LocalDate fechaFin = vencimientoantiguo.getFecha_fin();
        LocalDate fechaInicio = vencimientoantiguo.getFecha_inicio();

        Period periodo = Period.between(fechaInicio, fechaFin);
        Integer diferenciaMeses = periodo.getYears() * 12 + periodo.getMonths();

        LocalDate nuevaFechaFin = fechaFin.plusMonths(diferenciaMeses);

        Vencimiento vencimientoNuevo = new Vencimiento();
        vencimientoNuevo.setFecha_inicio(vencimientoantiguo.getFecha_fin());
        vencimientoNuevo.setFecha_fin(nuevaFechaFin);
        vencimientoNuevo.setEstado(Estado.ACTIVO);
        vencimientoNuevo.setSocio(vencimientoantiguo.getSocio());

        vencimientoRepository.save(vencimientoNuevo);

        VencimientoDTO vencimientoDTO = new VencimientoDTO();
        vencimientoDTO.setId(vencimientoNuevo.getId());
        vencimientoDTO.setFecha_fin(vencimientoNuevo.getFecha_fin());

        return vencimientoDTO;
    }

}



















