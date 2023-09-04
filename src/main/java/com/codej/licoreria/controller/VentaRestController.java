package com.codej.licoreria.controller;

import com.codej.licoreria.model.DVenta;
import com.codej.licoreria.model.Producto;
import com.codej.licoreria.model.Venta;
import com.codej.licoreria.service.IProductoService;
import com.codej.licoreria.service.IVentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class VentaRestController {

    private final IVentaService ventaService;

    private final IProductoService productService;

    @GetMapping
    List<Venta> finAll() {
        return ventaService.findAll();
    }

    @PostMapping
    Venta save(@RequestBody Venta venta) {
        venta.setNventa("N" + new Date().getTime());
        Venta ventaNew = null;
        Producto productActual = null;
        ventaNew = ventaService.save(venta);
        //Disminuir el stock
        for (DVenta detalleVenta : venta.getDventas()) {
            Integer productoId = detalleVenta.getProducto().getId();
            int cantidadVendida = (int) detalleVenta.getCantidad();

            Producto productoActual = productService.findById(productoId);

            if (productoActual != null) {
                int nuevoStock = productoActual.getStock() - cantidadVendida;
                if (nuevoStock >= 0) {
                    productoActual.setStock(nuevoStock);
                    int ventasActuales = productoActual.getNVentas();
                    productoActual.setNVentas(ventasActuales + cantidadVendida);
                    productService.save(productoActual);
                }
            }


        }
        return ventaNew;

    }

    @GetMapping("/{id}")
    Venta finById(@PathVariable Integer id) {
        return ventaService.findById(id);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        ventaService.delete(id);
    }

    @GetMapping("/fechas")
    @ResponseStatus(HttpStatus.OK)
    public List<Venta> showFechas2(@RequestParam(required = false) Date fecha1,
                                   @RequestParam(required = false) Date fecha2) {
        if (fecha1 == null || fecha2 == null) {
            return ventaService.findAll();
        }
        return ventaService.findAllVentasPorFechas(fecha1, fecha2);
    }
}
