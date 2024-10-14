package org.example.integrador3.repository;


import org.example.integrador3.DTO.ReporteDTO;
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
    public Carrera findCarreraById(Long idCarrera);


    @Query("SELECT new org.example.integrador3.DTO.ReporteDTO(c.nombreCarrera, YEAR(ec.fechaInicio), " +
            "COUNT(DISTINCT ec.estudiante), " +
            "SUM(CASE WHEN ec.fechaFin IS NOT NULL THEN 1 ELSE 0 END)) " +
            "FROM Carrera c " +
            "JOIN EstudianteCarrera ec " +
            "GROUP BY c.nombreCarrera, YEAR(ec.fechaInicio) " +
            "ORDER BY c.nombreCarrera ASC, YEAR(ec.fechaInicio) ASC")
    List<ReporteDTO> getReporte();
}

