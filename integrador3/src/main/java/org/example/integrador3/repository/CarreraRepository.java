package org.example.integrador3.repository;


import org.example.integrador3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT c, COUNT(ec) as inscriptos FROM Carrera c " +
            "JOIN EstudianteCarrera ec ON c.idCarrera = ec.carrera.idCarrera " +
            "GROUP BY c.idCarrera " +
            "HAVING COUNT(ec) > 0 " +
            "ORDER BY inscriptos DESC")

    public List<Object[]> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad();


    @Query("SELECT c FROM Carrera c WHERE c.idCarrera = :idCarrera")
    public  Carrera findCarreraById(Long idCarrera);
        }