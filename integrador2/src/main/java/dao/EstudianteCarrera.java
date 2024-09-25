package main.java.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class EstudianteCarrera implements Serializable {
    @EmbeddedId
    private EstudianteCarreraID id;

    @ManyToOne
    private Estudiante estudiante;
    @ManyToOne
    private Carrera carrera;

    @Column(nullable = false)
    private String nombreCarrera;
    @Column(nullable = false)
    private int antiguedad;
    @Column(nullable = false)
    private boolean graduado;


//    public EstudianteCarrera(boolean graduado, int antiguedad, String nombreCarrera, Carrera carrera, Estudiante estudiante, EstudianteCarreraID id) {
//        this.graduado = graduado; //FECHA DE INICIO
//        this.antiguedad = antiguedad; //FECHA DE FIN
//        this.nombreCarrera = nombreCarrera;
//        this.carrera = carrera;
//        this.estudiante = estudiante;
//        this.id = id;
//    }

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.antiguedad = 13;
        this.graduado = false;
        this.nombreCarrera = "ds";
        this.id = new EstudianteCarreraID(carrera.getIdCarrera(),estudiante.getLU());
    }

    public EstudianteCarrera() {
        super();
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }
}
