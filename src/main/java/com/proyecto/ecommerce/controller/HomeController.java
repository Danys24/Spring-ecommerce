/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.DetalleOrden;
import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.ProductoServicio;
import com.proyecto.ecommerce.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private ProductoServicio productoServicio;
    
    @Autowired
    private UsuarioService usuarioService;
            
    
    List<DetalleOrden> detalles = new ArrayList();
    
    Orden orden = new Orden();
            
    @GetMapping(" ")
    public String home(Model model){
        model.addAttribute("productos", productoServicio.getProductos());
        return "usuario/home";
    }
    
    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Integer id, Model model){
        
        LOGGER.info("id del producto enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> opt = productoServicio.getProducto(id);
        producto = opt.get();
        
        model.addAttribute("producto",producto);
        
        return "usuario/productohome";
    }
    
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        
        Optional<Producto> opt = productoServicio.getProducto(id);
        LOGGER.info("Producto añadido {}", opt.get());
        LOGGER.info("cantidad {}", cantidad);
        
        producto = opt.get();
        
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);
        
        Integer prodcutoid = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == prodcutoid);
        
        if(!ingresado){
            detalles.add(detalleOrden);
        }
        
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        
        orden.setTotal(sumaTotal);
        
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        
        return "usuario/carrito";
    }
    
    @GetMapping("/delete/cart/{id}")
    public String eliminarProducto(@PathVariable Integer id, Model model){
        
        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();
        
        for(DetalleOrden detalleOrden : detalles){
            if(detalleOrden.getProducto().getId() != id){
                ordenesNuevas.add(detalleOrden);
            }
        }
        
        detalles = ordenesNuevas;
        
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        
        
        return "usuario/carrito";
    }
    
    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }
    
    @GetMapping("/order")
    public String order(Model model){
        
        Usuario usuario = usuarioService.findById(1).get();
        
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        
        return "usuario/resumenorden";
    }
    
}
