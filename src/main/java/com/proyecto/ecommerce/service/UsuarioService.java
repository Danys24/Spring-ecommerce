/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyecto.ecommerce.service;

import com.proyecto.ecommerce.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ACER
 */
public interface UsuarioService {
    Optional<Usuario> findById(Integer id);
    void registrarUsuario(Usuario usuario);
    List<Usuario> findAll();
    Optional<Usuario> findByEmail(String email);
    
}
