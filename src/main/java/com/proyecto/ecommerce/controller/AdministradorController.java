/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.OrdenService;
import com.proyecto.ecommerce.service.ProductoServicio;
import com.proyecto.ecommerce.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private ProductoServicio productoServicio;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private OrdenService ordenService;
            
    @GetMapping("")
    public String home(Model model){
        
        List<Producto> productos = productoServicio.getProductos();
        model.addAttribute("productos",productos);
        
        return "administrador/home";
    }
    
    @GetMapping("/usuarios")
    public String usuarios(Model model){
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "administrador/usuarios";
    }
    
    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes", ordenService.findAll());
        return "administrador/ordenes";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model){
        Orden orden = ordenService.findById(id).get();
        model.addAttribute("detalles", orden.getDetalleOrden());
        return "administrador/detalleOrden";
    }
}
