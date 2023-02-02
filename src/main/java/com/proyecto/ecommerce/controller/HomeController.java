/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.service.ProductoServicio;
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
            
    @GetMapping(" ")
    public String home(Model model){
        model.addAttribute("productos", productoServicio.getProductos());
        return "usuario/home";
    }
    
    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Integer id){
        LOGGER.info("id del producto enviado como parametro {}", id);
        
        return "usuario/productohome";
    }
}
