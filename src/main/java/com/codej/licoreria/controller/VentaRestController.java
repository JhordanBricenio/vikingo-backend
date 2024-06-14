package com.codej.licoreria.controller;

import com.codej.licoreria.model.DVenta;
import com.codej.licoreria.model.Producto;
import com.codej.licoreria.model.Venta;
import com.codej.licoreria.service.IProductoService;
import com.codej.licoreria.service.IVentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
public class VentaRestController {

    private final IVentaService ventaService;

    private final IProductoService productService;

    @GetMapping
    List<Venta> finAll() {
        List<Venta> ventas = ventaService.findAll();
        ventas.sort(Comparator.comparing(Venta::getFecha).reversed());
        return ventas;
    }

    @PostMapping
    Venta save(@RequestBody Venta venta) {
        venta.setNventa("N" + new Date().getTime());
        Venta ventaNew = null;
        ventaNew = ventaService.save(venta);
        //Disminuir el stock
        for (DVenta detalleVenta : venta.getDventas()) {
            Integer productoId = detalleVenta.getProducto().getId();
            int cantidadVendida = (int) detalleVenta.getCantidad();

            Producto productoActual = productService.findById(productoId);

            if (productoActual != null) {
                int nuevoStock = productoActual.getStock() - cantidadVendida;
                if (nuevoStock > 0) {
                    productoActual.setStock(nuevoStock);
                    int ventasActuales = productoActual.getNVentas();
                    productoActual.setNVentas(ventasActuales + cantidadVendida);
                    productoActual.setCantidad(nuevoStock);
                    productoActual.setEstado("ACTIVO");
                    productService.save(productoActual);

                } else if (nuevoStock == 0) {
                    productoActual.setStock(nuevoStock);
                    int ventasActuales = productoActual.getNVentas();
                    productoActual.setNVentas(ventasActuales + cantidadVendida);
                    productoActual.setEstado("INACTIVO");
                    productoActual.setCantidad(0);
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
        List<Venta> ventas = ventaService.findAll();
        ventas.sort(Comparator.comparing(Venta::getFecha).reversed());

        if (fecha1 == null || fecha2 == null) {
            return ventas;
        }
        return ventaService.findAllVentasPorFechas(fecha1, fecha2);
    }

    //Cambiar el estado de la venta
    @Secured({"ROLE_ADMIN", "ROLE_VENDEDOR"})
    @PutMapping("/{id}/cambiarEstado")
    public Venta cambiarEstado(@PathVariable Integer id, @RequestParam String nuevoEstado) {
        Optional<Venta> optionalVenta = ventaService.findVentaById(id);
        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();
            venta.setEstado(nuevoEstado);
            ventaService.save(venta);
            return venta;
        }
        return null;
    }

    //Contar las ventas por estado
    @GetMapping("/contarVentasPorEstado")
    public List<Object[]> contarVentasPorEstado() {
        return ventaService.contarVentasPorEstado();
    }

    //Obtener las primeras 5 ventas



}
