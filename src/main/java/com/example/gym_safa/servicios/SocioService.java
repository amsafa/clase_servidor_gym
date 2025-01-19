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
import java.util.Optional;

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
        if (socios.getDni() == null || socios.getDni().trim().isEmpty()) {
            throw new RuntimeException("Es obligatorio");
        }

        if (socios.getEmail() == null || socios.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Es obligatorio");
        }


        // Verificar si el DNI ya existe en otro socio
        Optional<Socio> socioExistente = socioRepository.findByDNI(socios.getDni());
        if (socioExistente.isPresent() && !socioExistente.get().getId().equals(socios.getId())) {
            throw new RuntimeException("El DNI ya está registrado en otro socio");
        }

        // Verificar si el email ya existe en otro socio
        Optional<Socio> socioExistenteEmail = socioRepository.findByEmail(socios.getEmail());
        if (socioExistenteEmail.isPresent() && !socioExistenteEmail.get().getId().equals(socios.getId())) {
            throw new RuntimeException("El email ya está registrado en otro socio");
        }


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
     * @param
     */

    //Ejercicio 6

    public class SocioNotFoundException extends RuntimeException {
        public SocioNotFoundException(String message) {
            super(message);
        }
    }


    public String deleteSocios(Integer id) {
        try {
            Socio socio = socioRepository.findById(id)
                    .orElseThrow(() -> new SocioNotFoundException("Socio no encontrado."));

            socioRepository.deleteById(id);
            return "Socio eliminado";
        } catch (SocioNotFoundException e) {
            throw e;
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

        // Añadir un mensaje de éxito
        vencimientoDTO.setMensaje("Renovación exitosa");


        return vencimientoDTO;
    }


}



















