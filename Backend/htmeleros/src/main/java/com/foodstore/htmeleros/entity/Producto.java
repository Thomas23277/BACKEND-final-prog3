package com.foodstore.htmeleros.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

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

 // 🔹 Campo calculado (no se guarda en la BD)
 @Transient
 private boolean disponible;

 // 🔹 Getter personalizado: se calcula dinámicamente según el stock
 public boolean isDisponible() {
  return this.stock != null && this.stock > 0;
 }
}
