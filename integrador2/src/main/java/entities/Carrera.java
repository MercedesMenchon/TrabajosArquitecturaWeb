package main.java.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Carrera implements Serializable {
    @Id
    private Long idCarrera;
    @Column(nullable = false)
    private String nombreCarrera;
    @OneToMany (mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;



    public Carrera(Long idCarrera,String nombreCarrera) {
       this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.estudiantes = new ArrayList<EstudianteCarrera>();
    }

    public Carrera() {
        super();
        this.estudiantes = new ArrayList<EstudianteCarrera>();
    }

    public void addEstudiante(EstudianteCarrera estudiante) {
       if (!this.estudiantes.contains(estudiante)){
           this.estudiantes.add(estudiante);
       }
    }

    public boolean contieneEstudiante(Estudiante estudianteCarrera) {
        return this.estudiantes.contains(estudianteCarrera);
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + idCarrera +
                ", nombreCarrera='" + nombreCarrera + '\'' +
                ", estudiantes=" + estudiantes +
                '}';
    }
}
