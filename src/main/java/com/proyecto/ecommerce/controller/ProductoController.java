/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.controller;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.model.Usuario;
import com.proyecto.ecommerce.service.ProductoServicio;
import com.proyecto.ecommerce.service.UploadFileService;
import com.proyecto.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private UploadFileService upload;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public String show(Model model) {
        List<Producto> productos = productoServicio.getProductos();
        model.addAttribute("productos", productos);
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    /**
     * @RequestParam("img") lo que hace es ir al template create y del input de
     * tipo file se toma el id de valor img y almacena todo lo ingresado en el
     * input en la variable file.
     * @param producto
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession sesion) throws IOException {
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario usuario = usuarioService.findById(Integer.parseInt(sesion.getAttribute("idUsuario").toString())).get();
        producto.setUsuario(usuario);

        //imagen
        if (producto.getId() == null) {//cuando se crea un producto
            String nombreImagen = upload.saveImage(file);
            producto.setImage(nombreImagen);
        } else {

        }

        productoServicio.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.getProducto(id);
        producto = optionalProducto.get();

        LOGGER.info("Este es el objeto buscado: {}", producto);
        model.addAttribute("producto", producto);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

        Producto prod = new Producto();
        prod = productoServicio.getProducto(producto.getId()).get();

        if (file.isEmpty()) {//cuando se edita el producto pero no se cambia la imag en

            producto.setImage(prod.getImage());
        } else {
            //elimina la imagen si no es la por defecto
            if (!prod.getImage().equals("default.jpg")) {
                upload.deleteImage(prod.getImage());
            }
            
            producto.setUsuario(prod.getUsuario());
            String nombreImagen = upload.saveImage(file);
            producto.setImage(nombreImagen);
        }
        productoServicio.actualizarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto p = new Producto();
        p = productoServicio.getProducto(id).get();

        //elimina la imagen si no es la por defecto
        if (!p.getImage().equals("default.jpg")) {
            upload.deleteImage(p.getImage());
        }

        productoServicio.eliminarProducto(id);
        return "redirect:/productos";
    }
}
