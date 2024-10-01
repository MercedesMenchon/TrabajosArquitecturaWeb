package main.java.repositoryImplementaciones;

import main.java.DTO.CarreraDTO;
import main.java.DTO.EstudianteDTO;
import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.repository.EstudianteRepository;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Estudiante_RepositoryImplementacion implements EstudianteRepository {
    private EntityManagerFactory emf;

    public Estudiante_RepositoryImplementacion(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void insertEstudiante(Estudiante estudiante) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            if (em.find(Estudiante.class, estudiante.getLU()) == null) {
                em.persist(estudiante);
            } else {
                em.merge(estudiante);
                System.out.println("Ya se encuentra creado, se modificaron los datos");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();

            }
            e.printStackTrace();
            throw new RuntimeException("Error al insertar Estudiante: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();

            }
        }
    }


    public Estudiante getEstudiantePorLU(Long LU) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT e FROM Estudiante e WHERE e.LU = :lu";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("lu", LU);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // O maneja según tu lógica de negocio
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstudianteDTO> findAllEstudiantesDTOOrdenadosPorApellido() {
        String jpql = "SELECT e FROM Estudiante e ORDER BY e.apellido ASC";
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);
        List<Estudiante> estudiantes = query.getResultList();
        List<EstudianteDTO> listaEstudianteDTO = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            EstudianteDTO est = new EstudianteDTO(estudiante);
            listaEstudianteDTO.add(est);
        }
        return listaEstudianteDTO;
    }

    public EstudianteDTO findEstudianteDTOByLU(Long LU) {

        String jpql = "SELECT e FROM Estudiante e WHERE e.LU = :LU";
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);
        query.setParameter("LU", LU);
        Estudiante estudiante = query.getSingleResult();
        EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante);
        entityManager.close();

        return estudianteDTO;
    }

    public List<EstudianteDTO> findEstudiantesDTOByGenero(String genero) {

        String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero";
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);
        query.setParameter("genero", genero);
        List<Estudiante> estudiantes = query.getResultList();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante);
            estudiantesDTO.add(estudianteDTO);
        }
        return estudiantesDTO;
    }

    public List<EstudianteDTO> findEstudiantesPorCarreraYCiudad(Long idCarrera, String ciudadResidencia) {

        String jpql = "SELECT e FROM Estudiante e " +
                "JOIN EstudianteCarrera ec ON e.LU = ec.estudiante.LU " +
                "WHERE ec.carrera.id = :idCarrera " +
                "AND e.ciudadResidencia = :ciudadResidencia";


        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);
        query.setParameter("idCarrera", idCarrera);
        query.setParameter("ciudadResidencia", ciudadResidencia);

        List<Estudiante> estudiantes = query.getResultList();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante);
            estudiantesDTO.add(estudianteDTO);
        }

        return estudiantesDTO;
    }
}
