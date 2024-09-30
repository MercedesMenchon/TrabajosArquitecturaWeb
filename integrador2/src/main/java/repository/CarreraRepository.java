package main.java.repository;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;

import java.sql.Connection;

public interface CarreraRepository {
    public abstract void insertCarrera(Carrera carrera) ;
    public abstract Carrera findCarreraById(Long idCarrera);
}
