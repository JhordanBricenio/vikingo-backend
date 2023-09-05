package com.codej.licoreria.service.impl;

import com.codej.licoreria.model.Venta;
import com.codej.licoreria.repository.IVentaRepository;
import com.codej.licoreria.service.IVentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VentaServiceImpl implements IVentaService {

    private IVentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta findById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void delete(Integer id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public List<Venta> findAllVentasPorId(Integer id) {
        return null;
    }

    @Override
    public List<Venta> findAllVentasPorFechas(Date fecha1, Date fecha2) {
        return ventaRepository.findAllVentasPorFechas(fecha1, fecha2);
    }

    @Override
    public Optional<Venta> findVentaById(Integer id) {
        return ventaRepository.findById(id);
    }
}
