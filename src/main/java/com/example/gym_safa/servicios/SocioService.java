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
            dto.setDni(socio.getDNI());
            dto.setFechaNacimiento(socio.getFecha_nacimiento());
            dto.setCuentaBancaria(socio.getCuenta_bancaria());
            dto.setTelefono(socio.getTelefono());
            dto.setEmail(socio.getEmail());
            dto.setFechaRegistro(socio.getFecha_registro());
            dto.setVencimientos(new ArrayList<>());
            dto.setPagos(new ArrayList<>());

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
    socio.setDNI(socios.getDni());
    socio.setFecha_nacimiento(socios.getFechaNacimiento());
    socio.setCuenta_bancaria(socios.getCuentaBancaria());
    socio.setTelefono(socios.getTelefono());
    socio.setEmail(socios.getEmail());
    socio.setFecha_registro(socios.getFechaRegistro());
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
        vencimientoDTO.setFechaFin(vencimientoNuevo.getFecha_fin());

        return vencimientoDTO;
    }

    public VencimientoDTO renovarMembresiaSocio(Integer socioId) {
        // Obtiene la lista de vencimientos asociados al socio
        List<Vencimiento> listVencimientoaAntiguo = vencimientoRepository.findBySocioId(socioId);

        // Si no hay ningún vencimiento asociado al socio, lanza una excepción con un mensaje claro
        if (listVencimientoaAntiguo == null || listVencimientoaAntiguo.isEmpty()) {
            throw new RuntimeException("Este socio no tiene abono contratado");
        }

        // Ordena los vencimientos por fecha de fin de forma descendente
        listVencimientoaAntiguo.sort(Comparator.comparing(Vencimiento::getFecha_fin).reversed());

        // Obtiene el último vencimiento
        Vencimiento vencimientoAntiguo = listVencimientoaAntiguo.get(0);

        LocalDate fechaFin = vencimientoAntiguo.getFecha_fin();
        LocalDate fechaInicio = vencimientoAntiguo.getFecha_inicio();

        // Calcula la duración de la membresía anterior en meses
        Period periodo = Period.between(fechaInicio, fechaFin);
        Integer diferenciaMeses = periodo.getYears() * 12 + periodo.getMonths();

        // Calcula la nueva fecha de fin
        LocalDate nuevaFechaFin = fechaFin.plusMonths(diferenciaMeses);

        // Crea un nuevo vencimiento con las fechas actualizadas
        Vencimiento vencimientoNuevo = new Vencimiento();
        vencimientoNuevo.setFecha_inicio(vencimientoAntiguo.getFecha_fin());
        vencimientoNuevo.setFecha_fin(nuevaFechaFin);
        vencimientoNuevo.setEstado(Estado.ACTIVO);
        vencimientoNuevo.setSocio(vencimientoAntiguo.getSocio());

        // Guarda el nuevo vencimiento en el repositorio
        vencimientoRepository.save(vencimientoNuevo);

        // Crea y retorna el DTO con los datos del nuevo vencimiento
        VencimientoDTO vencimientoDTO = new VencimientoDTO();
        vencimientoDTO.setId(vencimientoNuevo.getId());
        vencimientoDTO.setFechaInicio(vencimientoNuevo.getFecha_inicio());
        vencimientoDTO.setFechaFin(vencimientoNuevo.getFecha_fin());
        vencimientoDTO.setEstado(vencimientoNuevo.getEstado().name());

        return vencimientoDTO;
    }


}



















