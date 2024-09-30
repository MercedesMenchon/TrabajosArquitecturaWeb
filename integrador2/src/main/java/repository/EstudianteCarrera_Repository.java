package main.java.repository;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;

import java.time.LocalDate;

public interface EstudianteCarrera_Repository {
    public abstract void anotarEstudianteCarrera(Estudiante estudiante, Carrera carrera, LocalDate fechaInicio, LocalDate fechaFin);

}
