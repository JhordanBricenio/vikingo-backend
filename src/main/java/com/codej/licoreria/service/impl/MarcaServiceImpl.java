package com.codej.licoreria.service.impl;


import com.codej.licoreria.model.Marca;
import com.codej.licoreria.repository.IMarcaRepository;
import com.codej.licoreria.service.IMarcaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MarcaServiceImpl implements IMarcaService {

    private IMarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca findById(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    public void delete(Integer id) {
        marcaRepository.deleteById(id);
    }
}
