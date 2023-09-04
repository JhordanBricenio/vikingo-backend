package com.codej.licoreria.service;

import com.codej.licoreria.model.Marca;

import java.util.List;

public interface IMarcaService {
    public List<Marca> findAll();
    public Marca findById(Integer id);
    public Marca save (Marca product);
    public void delete(Integer id);
}
