package com.example.gym_safa.servicios;

import com.example.gym_safa.dto.PagoDTO;
import com.example.gym_safa.modelos.Pago;
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
        dto.setFecha(pago.getFecha());
        dto.setMonto(pago.getMonto());
        dto.setSocioId(pago.getSocio().getId());
        dto.setTipoPago(pago.getTipo_pago().ordinal());
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
            dto.setFecha(pago.getFecha());
            dto.setMonto(pago.getMonto());
            dto.setSocioId(pago.getSocio().getId());
            dto.setTipoPago(pago.getTipo_pago().ordinal());
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
        pago.setFecha(pagoDTO.getFecha());
        pago.setMonto(pagoDTO.getMonto());
        pago.setSocio(socioRepository.findById(pagoDTO.getSocioId()).get());
        pago.setTipo_pago(TipoPago.values()[pagoDTO.getTipoPago()]);
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
}

