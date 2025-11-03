package com.foodstore.htmeleros.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

 @ManyToOne
 @JoinColumn(name = "categoria_id", nullable = false)
 private Categoria categoria;
}