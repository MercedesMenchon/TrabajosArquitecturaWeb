package main.java.repositoryImplementaciones;

import main.java.DTO.EstudianteDTO;
import main.java.entities.Estudiante;
import main.java.repository.EstudianteRepository;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        for (Estudiante estudiante : estudiantes) {
            EstudianteDTO est = new EstudianteDTO(estudiante);
        }

        return null;
    }
}

/*
    public void insertEstudiante(Estudiante estudiante, Connection conn) throws Exception {
        String insert = "INSERT INTO estudiante (LU,DNI,apellido,ciudadResidencia,edad, genero,nombre) VALUES (?, ?, ?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setLong(1, estudiante.getLU());
            ps.setInt(2, estudiante.getDNI());
            ps.setString(3, estudiante.getApellido());
            ps.setString(4, estudiante.getCiudadResidencia());
            ps.setInt(5, estudiante.getEdad());
            ps.setString(6, estudiante.getGenero());
            ps.setString(7, estudiante.getNombre());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
*/




