/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ecommerce.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ACER
 */
@Service
public class UploadFileService {
    private String folder = "images//";
    
    /**
     *La clase clase MultipartFile se utilizo en este metodo para la lectura de
     *imagenes.
     * 
     */
    
    public String saveImage(MultipartFile file) throws IOException{
       
        
        if(!file.isEmpty()){//si el objeto file es diferente de vacio ingresa al if
          byte[] bytesImage = file.getBytes();// transforma la imagen a bytes para que se pueda leer
          Path path = Paths.get(folder + file.getOriginalFilename());//almacena la imagen en la variable path
          Files.write(path, bytesImage);//Guarda la imagen
          return file.getOriginalFilename();//retorna el nombre de la imagen
        }
        
        return "default.jpg";
    }
    
    public void deleteImage(String nombre){
        String ruta = "images//";
        File file = new File(ruta+nombre);
        file.delete();
    }
}
