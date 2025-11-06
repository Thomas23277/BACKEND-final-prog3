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
import com.foodstore.htmeleros.service.CategoriaService;

@RestController
//http://localhost:8080/api/categoria
@RequestMapping("/api/categoria")  
public class CategoriaController {

    private final CategoriaService categoriaService;
    
    public CategoriaController(CategoriaService categoriaService) {this.categoriaService = categoriaService;}

    //http://localhost:8080/api/categoria
    @PostMapping
    public Categoria save(@RequestBody Categoria categoria) {return categoriaService.save(categoria);}


    //http://localhost:8080/api/categoria
    @GetMapping
    public List<Categoria> findAll() {return categoriaService.findAll();}

    


    //http://localhost:8080/api/categoria/1
    @GetMapping("/{id}")
    public Categoria findById(@PathVariable() Long id) {return categoriaService.findById(id);}


    //http://localhost:8080/api/categoria/1
    @DeleteMapping({"/{id}"})
    public void deleteById(@PathVariable() Long id){
        categoriaService.deleteById(id);
    }



    //http://localhost:8080/api/categoria
    @PutMapping
    public Categoria updateCategoria(@RequestBody Categoria categoria){
        Categoria categoriaDb= categoriaService.findById(categoria.getId());
        categoriaDb.setNombre(categoria.getNombre());
        return categoriaService.update(categoriaDb);
    }
}



    