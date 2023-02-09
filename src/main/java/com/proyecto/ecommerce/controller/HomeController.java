/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.DetalleOrden;
import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.DetalleOrdenService;
import com.proyecto.ecommerce.service.OrdenService;
import com.proyecto.ecommerce.service.ProductoServicio;
import com.proyecto.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    
    @Autowired
    private OrdenService ordenService;
    
    @Autowired
    private DetalleOrdenService detalleOrdenService;   
            
    
    List<DetalleOrden> detalles = new ArrayList();
    
    Orden orden = new Orden();
            
    @GetMapping(" ")
    public String home(Model model, HttpSession sesion){
        
        LOGGER.info("Sesion del usuario {}", sesion.getAttribute("idUsuario"));
        model.addAttribute("productos", productoServicio.getProductos());
        
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
        
        return "usuario/home";
    }
    
    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Integer id, Model model, HttpSession sesion){
        
        
        
        LOGGER.info("id del producto enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> opt = productoServicio.getProducto(id);
        producto = opt.get();
        
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
        model.addAttribute("producto",producto);
        
        return "usuario/productohome";
    }
    
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model, HttpSession sesion){
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
        
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
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
    public String getCart(Model model, HttpSession sesion){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
         
        return "usuario/carrito";
    }
    
    @GetMapping("/order")
    public String order(Model model, HttpSession sesion){
        
        Usuario usuario = usuarioService.findById(Integer.parseInt(sesion.getAttribute("idUsuario").toString())).get();
        
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
        
        return "usuario/resumenorden";
    }
    
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession sesion){
        Date fechaCracion = new Date();
        orden.setFechaCreacion(fechaCracion);
        orden.setNumero(ordenService.generarNumeroOrden());
        
        Usuario usuario = usuarioService.findById(Integer.parseInt(sesion.getAttribute("idUsuario").toString())).get();
        
        orden.setUsuario(usuario);
        ordenService.save(orden);
        
        for(DetalleOrden dt : detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        
        orden = new Orden();
        detalles.clear();
        
        return "redirect:/";
    }
    
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        
        LOGGER.info("Nombre del producto {}", nombre);
        
        List<Producto> productos = productoServicio.getProductos()
                                                   .stream().filter(p -> p.getNombre()
                                                   .contains(nombre)).collect(Collectors.toList());
        
        model.addAttribute("productos", productos);
         
        return "usuario/home";
    }
}
