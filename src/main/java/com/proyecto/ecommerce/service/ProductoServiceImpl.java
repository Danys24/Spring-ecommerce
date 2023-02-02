/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.service;

import com.proyecto.ecommerce.model.Producto;
import com.proyecto.ecommerce.respository.ProductoRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class ProductoServiceImpl implements ProductoServicio {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProducto(Integer id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public void actualizarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }
    
}
