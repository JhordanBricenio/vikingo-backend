package com.codej.licoreria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private double subtotal;
    @NotNull
    private double cantidad;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    private Integer venta_id;

    //Relacion con producto
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @PrePersist
    public void prePersist(){
        this.fecha = new Date();
    }

    //Calcular la ganancia
    public Double getGanancia() {
        return (cantidad* producto.getPrecioVenta()-cantidad * producto.getPrecio());
    }

    public Double getImporte() {
        return cantidad * producto.getPrecioVenta();
    }



    //Calculo de subtotal
    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }
}
