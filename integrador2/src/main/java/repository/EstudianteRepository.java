package main.java.repository;

import main.java.entities.Estudiante;

import java.sql.Connection;

public interface EstudianteRepository {
    public static void insertEstudiante(Estudiante estudiante, Connection conn){} ;
}
