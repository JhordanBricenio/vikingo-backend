package com.codej.licoreria.controller;

import com.codej.licoreria.model.Producto;
import com.codej.licoreria.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/producto")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoRestController {
    private final IProductoService productoService;

    @GetMapping
    List<Producto> findAll(){
        return productoService.findAll();
    }

    @PostMapping
    Producto save(@RequestBody Producto producto){
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable Integer id){
        Producto productoActual = productoService.findById(id);
        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());

        productoActual.setStock(producto.getCantidad());
        productoActual.setCategoria(producto.getCategoria());

        return productoService.save(producto);
    }
    @GetMapping("/ver/{id}")
    Producto findById(@PathVariable Integer id){
        return productoService.findById(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        productoService.delete(id);
    }

    @GetMapping("/{term}")
    public List<Producto> filtrarProductos(@PathVariable String term){
        return productoService.findByNombre(term);
    }

    @GetMapping("/buscar/page/{page}")
    public Page<Producto> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return productoService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, defaultValue = "0") Integer stock,
            @RequestParam(defaultValue = "0") int page   ) {

        try {
            List<Producto> productos;
            Pageable paging = PageRequest.of(page, 3);

            Page<Producto> pageProducts = null;
            if (nombre == null && stock == 0 )
                pageProducts = productoService.findAll(paging);
            else if (nombre == null && stock != 0)
                pageProducts = productoService.findByStockContaining(stock, paging);
            else if (nombre != null && stock == 0)
                pageProducts = productoService.findByNombreContaining(nombre, paging);

            productos = pageProducts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("products", productos);
            response.put("currentPage", pageProducts.getNumber());
            response.put("totalItems", pageProducts.getTotalElements());
            response.put("totalPages", pageProducts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
