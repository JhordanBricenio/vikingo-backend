package com.codej.licoreria.service;

import com.codej.licoreria.model.Categoria;


import java.util.List;

public interface ICategoriaService {
    public List<Categoria> findAll();
    public Categoria findById(Integer id);
    public Categoria save (Categoria categoria);
    public void delete(Integer id);

}
