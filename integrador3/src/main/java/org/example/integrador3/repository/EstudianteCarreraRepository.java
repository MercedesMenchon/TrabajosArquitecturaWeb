package org.example.integrador3.repository;


import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Long> {

    @Query("SELECT ec FROM EstudianteCarrera ec where ec.carrera = :carrera")
    public List<Estudiante> findAllBySurname(Carrera carrera);

    @Query("SELECT e FROM Estudiante e where e.nombre = :nombre")
    public List<Estudiante> findAllByName(String nombre);

    EstudianteCarrera matricularEstudianteEnCarrera(Estudiante estudiante, Carrera carrera, LocalDate fechaInicio, LocalDate fechaFin);
}
