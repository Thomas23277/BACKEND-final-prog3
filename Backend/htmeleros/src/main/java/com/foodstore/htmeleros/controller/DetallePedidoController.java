package com.foodstore.htmeleros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.service.DetallePedidoService;

@RestController
@RequestMapping("/api/detalle-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;


    //http://localhost:8080/api/detalle-pedido
    @PostMapping
    public DetallePedido crear(@RequestBody DetallePedido detalle) {
        return service.save(detalle);
    }


    //http://localhost:8080/api/detalle-pedido
    @GetMapping
    public List<DetallePedido> listar() {
        return service.findAll();
    }


    //http://localhost:8080/api/detalle-pedido/1
    @GetMapping("/{id}")
    public DetallePedido obtener(@PathVariable Long id) {
        return service.findById(id);
    }


    //http://localhost:8080/api/detalle-pedido/1
    @PutMapping("/{id}")
    public DetallePedido actualizar(@PathVariable Long id, @RequestBody DetallePedido detalle) {
        return service.update(id, detalle);
    }

    //http://localhost:8080/api/detalle-pedido/1
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.deleteById(id);
    }
}
