package org.example.integrador3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Estudiante implements Serializable {
    @Id
    private Long LU;
    @Column(nullable=false)
    private Long DNI;
    @Column(nullable=false)
    private String nombre;
    @Column(nullable=false)
    private String apellido;
    @Column(nullable=false)
    private Long edad;
    @Column(nullable=true)
    private String genero;
    @Column(nullable=false)
    private String ciudadResidencia;
    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List <EstudianteCarrera> carreras;


    public Estudiante(String nombre, String apellido, Long edad, String genero, Long DNI, String ciudadResidencia, Long LU) {
        this.LU= LU;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.DNI = DNI;
        this.ciudadResidencia = ciudadResidencia;
        this.carreras = new ArrayList<EstudianteCarrera>();

    }

    public Estudiante() {
        super();
        this.carreras = new ArrayList<EstudianteCarrera>();
    }


    public String getApellido() {
        return apellido;
    }


    public Long getLU() {
        return LU;
    }



    public String getCiudadResidencia() {
        return ciudadResidencia;
    }



    public Long getDNI() {
        return DNI;
    }



    public String getGenero() {
        return genero;
    }


    public Long getEdad() {
        return edad;
    }


    public String getNombre() {
        return nombre;
    }


    @Override
    public String toString() {
        return "Estudiante{" +
                "LU=" + LU  +
                ", DNI=" + DNI +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                '}';
    }

}




