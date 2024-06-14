package com.codej.licoreria.controller;

import com.codej.licoreria.model.Categoria;
import com.codej.licoreria.model.Marca;
import com.codej.licoreria.service.IMarcaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/marcas")
public class MarcaRestController {

    private final IMarcaService marcaService;

    @GetMapping
    List<Marca> getAll(){
        return marcaService.findAll();
    }

    @PostMapping
    Marca save(@RequestBody Marca marca){
        return marcaService.save(marca);
    }

    @GetMapping("/{id}")
    Marca finById(@PathVariable Integer id){
        return marcaService.findById(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        marcaService.delete(id);
    }

}
