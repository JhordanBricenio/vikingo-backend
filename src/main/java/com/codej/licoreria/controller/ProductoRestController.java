package com.codej.licoreria.controller;

import com.codej.licoreria.model.Producto;
import com.codej.licoreria.service.IProductoService;
import com.codej.licoreria.service.IUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/producto")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoRestController {
    private final IProductoService productoService;

    private final IUploadService uploadService;

    @GetMapping
    List<Producto> findAll(){
        return productoService.findAll();
    }

    @PostMapping
    Producto save(@RequestBody Producto producto){
        producto.setStock(producto.getCantidad());
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable Integer id){
        Producto productoActual = productoService.findById(id);
        if (!producto.getCantidad().equals(productoActual.getCantidad())) {
            producto.setStock(producto.getCantidad());
        } else {
            producto.setNVentas(0);
        }

        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setEstado(producto.getEstado());
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
        Pageable pageable = PageRequest.of(page, 10);
        return productoService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, defaultValue = "0") Integer stock,
            @RequestParam(defaultValue = "0") int page   ) {

        try {
            List<Producto> productos;
            Pageable paging = PageRequest.of(page, 10);

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


    @GetMapping("upload/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso= null;
        try{
            recurso= new UrlResource(rutaArchivo.toUri());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        if (!recurso.exists() && !recurso.isReadable()){
            throw new RuntimeException("Error: no se puede cargar la imagen: "+ nombreFoto);
        }
        HttpHeaders cabecera= new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+ "\"");
        return  new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo, @RequestParam("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Producto producto = productoService.findById(id);
        if(!archivo.isEmpty()){
            String nombreArchivo= null;
            try {
                nombreArchivo= uploadService.copiar(archivo);
            }catch (IOException e){
                response.put("mensaje", "Error al subir la imagen del proyecto ");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnt= producto.getImagen();
            uploadService.eliminar(nombreFotoAnt);
            producto.setImagen(nombreArchivo);
            productoService.save(producto);
            response.put("producto", producto);
            response.put("mensaje", "Has subido corectamente la imagen"+nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }



}
