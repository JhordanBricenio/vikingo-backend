package com.codej.licoreria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 5, max = 50, message = "El sku debe tener entre 5 y 50 caracteres")
    private String sku;
    @NotNull
    @Size(min = 5, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
    private String descripcion;
    private String imagen;
    @NotNull
    private Integer cantidad;

    private Double cantidadCaja;

    @NotNull
    private Double precio;
    @NotNull

    @NotNull
    private Double precioVenta;

    private Integer stock;
    private Integer nVentas=0;
    private Double descuento;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    private String estado="ACTIVO";



    @NotNull(message = "La categoria no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorias_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;


    @NotNull(message = "La marca no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;


    @PrePersist
    public void prePersist(){
        this.createdAt = new Date();
        this.sku = generarSku();


    }
    //Generar sku
    public String generarSku(){
        String sku = "";
        String[] palabras = this.nombre.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            if (i < 3){
                sku += palabras[i].substring(0, 3)+"-"+this.createdAt.getTime();
            }
        }
        return sku.toUpperCase();
    }


}
