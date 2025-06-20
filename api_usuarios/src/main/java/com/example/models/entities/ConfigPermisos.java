package com.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="permisos")
@Data
public class  ConfigPermisos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombreModulo;
    private String funcion;
    private String rol; /*Administrador, usuario, etc. */



    

    
}
