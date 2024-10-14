package org.example.integrador3.repository;

import org.example.integrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    @Query("SELECT e FROM Estudiante e WHERE e.genero =:genero")
    List<Estudiante> findByGenero(String genero);

    @Query("SELECT e FROM Estudiante e ORDER BY e.apellido ASC")
    public List<Estudiante> findAllEstudiantesDtoOrdenadoPorApellido();

    @Query("SELECT e FROM Estudiante e WHERE e.LU =:lu")
    public Estudiante getEstudiantePorLU(Long lu);

    @Query("SELECT e FROM Estudiante e " +
            "JOIN EstudianteCarrera ec ON e.LU = ec.estudiante.LU " +
            "WHERE ec.carrera.idCarrera = :idCarrera " +
            "AND e.ciudadResidencia = :ciudadResidencia")
    List<Estudiante> findEstudiantesPorCarreraYCiudad(Long idCarrera, String ciudadResidencia);
}
