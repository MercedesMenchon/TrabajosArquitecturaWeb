package main.java.repositoryImplementaciones;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.repository.CarreraRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Connection;

public class Carrera_RepositoryImplementacion   implements CarreraRepository {
    private EntityManagerFactory emf;


    public Carrera_RepositoryImplementacion(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void insert(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (em.find(Carrera.class, carrera.getIdCarrera()) == null) {
                em.persist(carrera);
            } else {
                em.merge(carrera);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
