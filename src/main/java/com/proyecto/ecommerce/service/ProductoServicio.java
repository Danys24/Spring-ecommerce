/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyecto.ecommerce.service;

import com.proyecto.ecommerce.model.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ACER
 */
public interface ProductoServicio {
    public Producto save(Producto producto);
    public Optional<Producto> getProducto(Integer id);
    public List<Producto> getProductos();
    public void actualizarProducto(Producto producto);
    public void eliminarProducto(Integer id);
}
