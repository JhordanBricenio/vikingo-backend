package com.codej.licoreria.service.impl;

import com.codej.licoreria.model.Categoria;
import com.codej.licoreria.model.Marca;
import com.codej.licoreria.model.Producto;
import com.codej.licoreria.repository.IProductoRepository;
import com.codej.licoreria.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductoServiceImpl implements IProductoService {

    private IProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Page<Producto> findAllByTitulo(String filtro, Pageable pageable) {
        return null;
    }

    @Override
    public Producto findById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);

    }

    @Override
    public List<Categoria> findAllCategories() {
        return null;
    }

    @Override
    public List<Marca> findAllMarcas() {
        return null;
    }

    @Override
    public Page<Producto> findByNombreContaining(String filtro, Pageable pageable) {
        return productoRepository.findByNombreContaining(filtro, pageable);
    }

    @Override
    public Page<Producto> findByStockContaining(Integer stock, Pageable pageable) {
        return productoRepository.findByStock(stock, pageable);
    }


}
