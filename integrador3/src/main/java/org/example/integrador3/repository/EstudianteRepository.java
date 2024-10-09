package org.example.integrador3.repository;

import org.example.integrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT e FROM Estudiante e where e.nombre = :apellido")
    public List<Estudiante> findAllBySurname(String apellido);

    @Query("SELECT e FROM Estudiante e where e.nombre = :nombre")
    public List<Estudiante> findAllByName(String nombre);

    @Query("SELECT e FROM Estudiante e ORDER BY e.apellido ASC")
    public List<Estudiante> findAllEstudiantesDtoOrdenadoPorApellido();
    @Query("SELECT e FROM Estudiante e WHERE e.LU = :lu")
    public Estudiante getEstudiantePorLU (Long lu);
    public Estudiante findByNombre(String nombre);
    public Estudiante findByApellido(String apellido);



}
