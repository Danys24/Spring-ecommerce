/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyecto.ecommerce.service;

import com.proyecto.ecommerce.model.Orden;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface OrdenService {
    Orden save(Orden orden);
    List<Orden> findAll();
    String generarNumeroOrden();
}
