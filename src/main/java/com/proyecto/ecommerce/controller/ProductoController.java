/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.ProductoServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    
    @Autowired
    private ProductoServicio productoServicio;  
            
    @GetMapping("")
    public String show(Model model){
        List<Producto> productos = productoServicio.getProductos();
        model.addAttribute("productos", productos);
        return "productos/show";
    }
    
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }
    
    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("Este es el objeto producto {}",producto);
        Usuario usuario = new Usuario();
        usuario.setId(1);
        producto.setUsuario(usuario);
        productoServicio.save(producto);
        return "redirect:/productos";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model ){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.getProducto(id);
        producto = optionalProducto.get();
        
        LOGGER.info("Este es el objeto buscado: {}", producto);
        model.addAttribute("producto",producto);
        
        return "productos/edit";
    }
    
    @PostMapping("/update")
    public String update(Producto producto){
        productoServicio.actualizarProducto(producto);
        return "redirect:/productos";
    }
}
