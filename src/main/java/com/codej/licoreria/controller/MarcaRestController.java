package com.codej.licoreria.controller;

import com.codej.licoreria.model.Marca;
import com.codej.licoreria.service.IMarcaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/marcas")
@CrossOrigin("*")
public class MarcaRestController {

    private final IMarcaService marcaService;

    @GetMapping
    List<Marca> getAll(){
        return marcaService.findAll();
    }

}
