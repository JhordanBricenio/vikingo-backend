package com.codej.licoreria.controller;

import com.codej.licoreria.model.Categoria;
import com.codej.licoreria.service.ICategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
