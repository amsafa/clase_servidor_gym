package com.example.gym_safa.servicios;

import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.repositorios.PagoRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    /**
     * Busca un pago por su id
     *
     * @param id
     * @return
     */
    public Pago getPagosById(Integer id) {
        return pagoRepository.findById(id).orElse(null);
    }

    /**
     * Devuelve todos los pagos
     *
     * @return
     */
    public Iterable<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    /**
     * Guarda un pago nuevo o modifica
     *
     * @param pago
     * @return
     */
    public Pago savePagos(Pago pago) {
        return pagoRepository.save(pago);
    }

    /**
     * Elimina un pago por su id
     *
     * @param id
     */
    public void deletePagos(Integer id) {
        pagoRepository.deleteById(id);
    }
}