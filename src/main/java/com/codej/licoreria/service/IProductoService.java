package com.codej.licoreria.service;

import com.codej.licoreria.model.Categoria;
import com.codej.licoreria.model.Marca;
import com.codej.licoreria.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductoService {
    public List<Producto> findAll();

    public Page<Producto> findAll(Pageable pageable);

    List<Producto> findByNombre(String nombre);

    public Page<Producto> findAllByTitulo(String filtro, Pageable pageable);

    public Producto findById(Integer id);

    public Producto save(Producto producto);

    public void delete(Integer id);

    public List<Categoria> findAllCategories();

    public List<Marca> findAllMarcas();


    Page<Producto> findByNombreContaining(String filtro, Pageable pageable);

    Page<Producto> findByStockContaining(Integer stock, Pageable pageable);

}
