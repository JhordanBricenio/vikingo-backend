package com.codej.licoreria.service;

import com.codej.licoreria.model.Venta;

import java.util.Date;
import java.util.List;

public interface IVentaService {
    public List<Venta> findAll();
    public Venta findById(Integer id);
    public Venta save (Venta venta);
    public void delete(Integer id);

    //Obtener todas las ventas de un cliente
    List<Venta> findAllVentasPorId(Integer id);

    //Filtrar ventas por fechas
    List<Venta> findAllVentasPorFechas(Date fecha1, Date fecha2);
}
