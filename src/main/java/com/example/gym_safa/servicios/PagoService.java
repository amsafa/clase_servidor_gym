package com.example.gym_safa.servicios;

import com.example.gym_safa.modelos.Pago;
import com.example.gym_safa.repositorios.PagoRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor

public class PagoService {

    private PagoRepository pagoRepository;

    /**
     * Metodo que devuelve todos los pagos
     *
     * @return
     */
    public List<Pago> getAllPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos;
    }

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
