package com.foodstore.htmeleros.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")

public class Producto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column(unique = true, nullable = false)
 private String nombre;
 @Column(nullable = false)
 private Double precio;
 @Column(nullable = false)
private Integer stock;

 @ManyToOne
 @JoinColumn(name = "categoria_id", nullable = false)
 private Categoria categoria;
}