package org.example.integrador3.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class EstudianteCarrera implements Serializable {
    @EmbeddedId
    private EstudianteCarreraID id;

    @ManyToOne
    private Estudiante estudiante;
    @ManyToOne
    private Carrera carrera;

    @Column(nullable = false)
    private LocalDate fechaInicio;
    @Column(nullable = true)
    private LocalDate fechaFin;

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, LocalDate inicio) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInicio=inicio;
        this.fechaFin=null;
        this.id = new EstudianteCarreraID(carrera.getIdCarrera(),estudiante.getLU());
    }
    public EstudianteCarrera(Estudiante estudiante, Carrera carrera) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInicio=LocalDate.now();
        this.fechaFin=null;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


}