package main.java.repositoryImplementaciones;

import main.java.entities.Estudiante;
import main.java.repository.EstudianteRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Estudiante_RepositoryImplementacion implements EstudianteRepository {
    private EntityManagerFactory emf;

    public Estudiante_RepositoryImplementacion(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void insert(Estudiante estudiante) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (em.find(Estudiante.class, estudiante.getLU()) == null) {
                em.persist(estudiante);
            } else {
                em.merge(estudiante);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
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
    }



