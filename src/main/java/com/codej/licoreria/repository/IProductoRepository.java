package com.codej.licoreria.repository;

import com.codej.licoreria.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {

       @Query("select p from Producto p where p.nombre like %?1%")
       public List<Producto> findByNombre(String term);

       public List<Producto> findByNombreContainingIgnoreCase(String term);

       Page<Producto> findByNombreContaining(String filtro, Pageable pageable);

       Page<Producto> findByStock(Integer stock, Pageable pageable);




}
