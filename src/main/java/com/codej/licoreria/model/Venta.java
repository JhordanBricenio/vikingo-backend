package com.codej.licoreria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nventa;
    private Double totalPagar;
    private String envio_titulo;
    private double envio_precio;
    private String transaccion;
    private String metodoPago;
    private String estado;
    private String nota;
    private String observacion;
    private String direccion;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @PrePersist
    public void prePersist() {
        fecha = new Date();
    }


   /* @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"ventas","hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "clientes_id")
    private Cliente cliente;*/

    //Relacion con dventa
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "venta_id")
    private List<DVenta> dventas;



    public Venta() {
        this.dventas = new ArrayList<>();
    }

    public Venta get() {
        return this;
    }
}
