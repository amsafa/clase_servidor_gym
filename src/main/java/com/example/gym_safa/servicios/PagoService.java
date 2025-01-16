package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.PagoDTO;
import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.modelos.Vencimiento;
import com.example.gym_safa.repositorios.PagoRepository;
import com.example.gym_safa.repositorios.SocioRepository;
import org.springframework.stereotype.Service;
import com.example.gym_safa.enumerados.TipoPago;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PagoService {

    private PagoRepository pagoRepository;
    private SocioRepository socioRepository;

    /**
     * Busca un pago por su id
     *
     * @param id
     * @return
     */
    public PagoDTO getPagosById(Integer id) {
        Pago pago = pagoRepository.findById(id).get();
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setSocioId(pago.getSocio().getId());
        return dto;
    }

    /**
     * Devuelve todos los pagos
     *
     * @return
     */
    public List<PagoDTO> getAllPagos() {
        List<PagoDTO> pagoDTOS = new ArrayList<>();
        List<Pago> pagos = pagoRepository.findAll();
        for (Pago pago : pagos) {
            PagoDTO dto = new PagoDTO();
            dto.setId(pago.getId());
            dto.setMonto(pago.getMonto());
            dto.setSocioId(pago.getSocio().getId());
            pagoDTOS.add(dto);
        }
        return pagoDTOS;
    }


    /**
     * Guarda un pago nuevo o modifica
     *
     * @param pagoDTO
     * @return
     */

    public PagoDTO guardarPago(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        pago.setId(pagoDTO.getId());
        pago.setMonto(pagoDTO.getMonto());
        pago.setSocio(socioRepository.findById(pagoDTO.getSocioId()).get());

        pagoRepository.save(pago);
        return pagoDTO;
    }

    /**
     * Elimina un pago por su id
     *
     * @param id
     */
    public void eliminarPago(Integer id) {
        pagoRepository.deleteById(id);

    }

    /**
     * Devuelve todos los pagos de un socio
     * @param socioId
     * @return
     */


    public List<PagoDTO> getPagosBySocioId(Integer socioId) {
        List<PagoDTO> pagoDTOS = new ArrayList<>();
        List<Pago> pagos = pagoRepository.findBySocioId(socioId);
        for (Pago pago : pagos) {
            PagoDTO dto = new PagoDTO();
            dto.setId(pago.getId());
            dto.setMonto(pago.getMonto());
            dto.setSocioId(pago.getSocio().getId());
            pagoDTOS.add(dto);
        }
        return pagoDTOS;
    }

    /**
     * Devuelve el total de pagos de un socio
     * @param socioId
     * @return
     */

    //Ejercicio 3

    public Double getTotalPagoBySocioId(Integer socioId) {
        List<Pago> pagos = pagoRepository.findBySocioId(socioId);
        double totalMonto = pagos.stream()
                .mapToDouble(Pago::getMonto)
                .sum();
        return totalMonto;
    }


    public Double importeGastado(Integer idSocio) {
        return socioRepository.findById(idSocio)
                .map(socio -> {
                    // Verificar que el socio tenga vencimientos
                    if (socio.getVencimientos() == null || socio.getVencimientos().isEmpty()) {
                        throw new RuntimeException("El socio con ID " + idSocio + " no tiene membresías registradas.");
                    }

                    // Obtener el último vencimiento
                    Vencimiento ultimoVencimiento = socio.getVencimientos()
                            .get(socio.getVencimientos().size() - 1);

                    // Validar que el último vencimiento tenga un pago asociado
                    boolean tienePago = socio.getPagos().stream()
                            .anyMatch(pago -> pago.getFechaPago().isAfter(ultimoVencimiento.getFecha_inicio()) &&
                                    pago.getFechaPago().isBefore(ultimoVencimiento.getFecha_fin()));

                    if (!tienePago) {
                        throw new RuntimeException("El socio con ID " + idSocio + " no ha pagado su última membresía.");
                    }

                    // Calcular el total gastado
                    return socio.getVencimientos().stream()
                            .mapToDouble(vencimiento -> vencimiento.getMembresia().getPrecio())
                            .sum();
                })
                .orElseThrow(() -> new RuntimeException("El socio con ID " + idSocio + " no existe en el sistema."));
    }





}

