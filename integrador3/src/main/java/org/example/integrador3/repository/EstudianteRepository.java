package org.example.integrador3.repository;


import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT e FROM Estudiante e where e.nombre = :apellido")
    public List<Estudiante> findAllBySurname(String apellido);

    @Query("SELECT e FROM Estudiante e where e.nombre = :nombre")
    public List<Estudiante> findAllByName(String nombre);



    public Estudiante findByNombre(String nombre);
    public Estudiante findByApellido(String apellido);
    public Estudiante getEstudiantePorLU (Long lu);


}
