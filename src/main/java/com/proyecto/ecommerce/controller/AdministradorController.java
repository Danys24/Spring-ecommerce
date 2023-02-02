/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.service.ProductoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            
    @GetMapping("")
    public String home(Model model){
        
        List<Producto> productos = productoServicio.getProductos();
        model.addAttribute("productos",productos);
        
        return "administrador/home";
    }
}
