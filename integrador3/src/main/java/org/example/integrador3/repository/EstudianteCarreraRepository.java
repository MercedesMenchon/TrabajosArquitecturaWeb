package org.example.integrador3.repository;


import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Long> {


    @Query("SELECT ec FROM EstudianteCarrera ec WHERE ec.estudiante.LU= :idEstudiante AND ec.carrera.idCarrera = :idCarrera")
    EstudianteCarrera findByCarreraIdAndEstudianteId(@Param("idCarrera") Long idCarrera, @Param("idEstudiante") Long idEstudiante);



@Query ("SELECT ec FROM EstudianteCarrera ec")
    public List<EstudianteCarrera> findAll();

}
