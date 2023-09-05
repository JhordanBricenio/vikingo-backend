package com.codej.licoreria.repository;

import com.codej.licoreria.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {

    @Modifying
    @Transactional
    @Query("select v from Venta v where v.fecha between :fecha1 and :fecha2")
    List<Venta> findAllVentasPorFechas(@RequestParam("fecha1") Date fecha1 , @RequestParam ("fecha1")Date fecha2);



}
