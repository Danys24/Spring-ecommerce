/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Orden;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.OrdenService;
import com.proyecto.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    @Autowired
    private OrdenService ordenService;
    
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
    
    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }
    
    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession sesion){
        LOG.info("Accesos: {}", usuario);
        
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        //LOG.info("Usuario de la base de datos: {}", user.get());
        
        if(user.isPresent()){
            
            sesion.setAttribute("idUsuario", user.get().getId());
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else{
                return "redirect:/";
            }
        }else{
            LOG.error("El usuario no existe");
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/compras")
    public String obtenerCompras(HttpSession sesion, Model model){
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
        
        Usuario usuario = usuarioService.findById(Integer.parseInt(sesion.getAttribute("idUsuario").toString())).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);
       
        //LOG.info("la orden es {}", ordenes);
        
        model.addAttribute("ordenes", ordenes);
        
        return "usuario/compras";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession sesion, Model model){
        LOG.info("id de la orden {}", id);
        Optional<Orden> orden = ordenService.findById(id);
        
        model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
        model.addAttribute("detalles",orden.get().getDetalleOrden());
        
        
        return "usuario/detalleCompra";
    }
}
