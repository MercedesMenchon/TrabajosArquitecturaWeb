package org.example.integrador3.repository;


import org.example.integrador3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT c FROM Carrera c where c.nombreCarrera = :nombreCarrera")
    public List<Carrera> findAllByName(String nombreCarrera);

   public Carrera findCarreraById (Long id);

}
