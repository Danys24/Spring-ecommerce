/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/registro")
    public String registro(){
        return "usuario/registro";
    }
    
    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario){
        
        LOG.info("Usuario registrado {}", usuario);
        
        usuario.setTipo("USER");
        usuarioService.registrarUsuario(usuario);
        
        return "redirect:/";
    }
}
