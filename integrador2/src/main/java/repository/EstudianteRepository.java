package main.java.repository;

import main.java.entities.Estudiante;

import java.sql.Connection;
import java.sql.SQLException;

public interface EstudianteRepository {
    public abstract void insertEstudiante(Estudiante estudiante) throws SQLException;
    public abstract Estudiante getEstudiantePorLU(Long LU);
}
