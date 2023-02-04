/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.service;


import com.proyecto.ecommerce.model.DetalleOrden;
import com.proyecto.ecommerce.respository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author ACER
 */
@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService{
    
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
    
}
