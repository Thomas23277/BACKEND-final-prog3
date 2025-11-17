package com.foodstore.htmeleros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
private Long id;
private String nombre;
private double precio;
private CategoriaDTO categoria;
private int stock;
}
