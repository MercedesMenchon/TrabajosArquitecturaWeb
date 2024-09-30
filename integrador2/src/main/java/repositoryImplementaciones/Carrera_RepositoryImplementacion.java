package main.java.repositoryImplementaciones;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.repository.CarreraRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.sql.Connection;

public class Carrera_RepositoryImplementacion implements CarreraRepository {
    private EntityManagerFactory emf;

    public Carrera_RepositoryImplementacion(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void insertCarrera(Carrera carrera) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            if (em.find(Carrera.class, carrera.getIdCarrera()) == null) {
                em.persist(carrera);
            } else {
                em.merge(carrera);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();

            }
            e.printStackTrace();
            throw new RuntimeException("Error al insertar carrera: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();

            }
        }
    }

    public Carrera findCarreraById(Long idCarrera) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Carrera.class, idCarrera);
        } catch (NoResultException e) {
            return null; // O maneja según tu lógica de negocio
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
