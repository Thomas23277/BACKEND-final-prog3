package com.foodstore.htmeleros.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.entity.Categoria;
import com.foodstore.htmeleros.entity.Producto;
import com.foodstore.htmeleros.repository.ProductoRepository;
import com.foodstore.htmeleros.service.CategoriaService;
import com.foodstore.htmeleros.service.ProductoService;

@RestController
//http://localhost:8080/api/producto
@RequestMapping("/api/producto")  
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.productoRepository = productoRepository;
    }

    //http://localhost:8080/api/producto
    @PostMapping
    public Producto save(@RequestBody Producto producto) {
        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaService.findById(categoriaId);
        producto.setCategoria(categoria);
        return productoService.save(producto);
    }


    //http://localhost:8080/api/producto/categoria/1
    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> findByCategoria(@PathVariable Long categoriaId) {
    Categoria categoria = categoriaService.findById(categoriaId);
    return productoRepository.findByCategoria(categoria);
}


    //http://localhost:8080/api/producto
    @GetMapping
    public List<Producto> findAll() {return productoService.findAll();}


    //http://localhost:8080/api/producto/1
    @GetMapping("/{id}")
    public Producto findById(@PathVariable() Long id) {return productoService.findById(id);}


    //http://localhost:8080/api/producto/1
    @DeleteMapping({"/{id}"})
    public void deleteById(@PathVariable() Long id){
        productoService.deleteById(id);
    }



    //http://localhost:8080/api/producto
    @PutMapping
public Producto updateProducto(@RequestBody Producto producto) {
    Producto productoDb = productoService.findById(producto.getId());
    productoDb.setNombre(producto.getNombre());
    productoDb.setPrecio(producto.getPrecio());

    Long categoriaId = producto.getCategoria().getId();
    Categoria categoria = categoriaService.findById(categoriaId);
    productoDb.setCategoria(categoria);

    return productoService.update(productoDb);
}

@PostMapping("/{id}/vender/{cantidad}")
public Producto vender(@PathVariable Long id, @PathVariable int cantidad) {
    return productoService.venderProducto(id, cantidad);
}

@PostMapping("/{id}/agregar-stock/{cantidad}")
public Producto agregarStock(@PathVariable Long id, @PathVariable int cantidad) {
    return productoService.agregarStock(id, cantidad);
}




}

