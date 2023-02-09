/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.service;

import com.proyecto.ecommerce.model.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{
    
    @Autowired
    private UsuarioService usuarioService;
    
   // @Autowired
    //private BCryptPasswordEncoder bCrypt;
     
    @Autowired
    HttpSession sesion;
    
    private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserName: {}", username);
        Optional<Usuario> optUser = usuarioService.findByEmail(username);
        
        if(optUser.isPresent()){
            log.info("id del usuario: {}", optUser.get().getId());
            sesion.setAttribute("idUsuario", optUser.get().getId());
            Usuario usuario = optUser.get();
            
            //return new UserDetailsImpl(usuario);
            
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getPassword())
                    .roles(usuario.getTipo()).build();

            
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
    
}
