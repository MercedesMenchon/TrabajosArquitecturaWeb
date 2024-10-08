package org.example.integrador3.repository;

import org.example.integrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT e FROM Estudiante e ORDER BY e.apellido ASC")
    public List<Estudiante> findAllEstudiantesDtoOrdenadoPorApellido();
    @Query("SELECT e FROM Estudiante e WHERE e.LU = :lu")
    public Estudiante getEstudiantePorLU (Long lu);
    @Query("SELECT e FROM Estudiante e WHERE e.genero = :genero")
    public Optional<Estudiante> findByGenero(String genero);
}
