package main.java.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long LU;
    @Column(nullable=false)
    private int DNI;
    @Column(nullable=false)
    private String nombre;
    @Column(nullable=false)
    private String apellido;
    @Column(nullable=false)
    private int edad;
    @Column(nullable=true)
    private String genero;
    @Column(nullable=false)
    private String ciudadResidencia;
    @OneToMany (mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List <EstudianteCarrera> carreras;



    public Estudiante(String nombre, String apellido, int edad, String genero, int DNI, String ciudadResidencia, long LU) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.DNI = DNI;
        this.ciudadResidencia = ciudadResidencia;
        this.LU = LU;
        this.carreras = new ArrayList<EstudianteCarrera>();

    }

    public Estudiante() {
        super();
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getLU() {
        return LU;
    }

    public void setLU(long LU) {
        this.LU = LU;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addCarrera(EstudianteCarrera carrera) {
        this.carreras.add(carrera);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "LU=" + LU +
                ", DNI=" + DNI +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                '}';
    }
}



