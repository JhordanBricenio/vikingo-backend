package com.codej.licoreria.controller;

import com.codej.licoreria.model.Categoria;
import com.codej.licoreria.service.ICategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoriaRestController {
    private final ICategoriaService categoriaService;

    @GetMapping
    List<Categoria> findAll(){
        return categoriaService.findAll();
    }

    @PostMapping
    Categoria save(@RequestBody Categoria categoria){
        return categoriaService.save(categoria);
    }

    @GetMapping("/{id}")
    Categoria finById(@PathVariable Integer id){
        return categoriaService.findById(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        categoriaService.delete(id);
    }




}
