package main.java.repository;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;

import java.sql.Connection;

public interface CarreraRepository {
    public static void insertCarrera(Carrera carrera, Connection conn){} ;

}
