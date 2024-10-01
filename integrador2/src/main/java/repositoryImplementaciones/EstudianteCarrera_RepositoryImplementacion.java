package main.java.repositoryImplementaciones;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.entities.EstudianteCarrera;
import main.java.entities.EstudianteCarreraID;
import main.java.repository.EstudianteCarrera_Repository;

import javax.persistence.*;
import java.sql.SQLException;
import java.time.LocalDate;


public class EstudianteCarrera_RepositoryImplementacion implements EstudianteCarrera_Repository {
    private EntityManagerFactory emf;
    private static EstudianteCarrera_RepositoryImplementacion ECRI= null;

    private EstudianteCarrera_RepositoryImplementacion(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static EstudianteCarrera_RepositoryImplementacion newEstudianteCarrera_RepositoryImplementacion(EntityManagerFactory emf){
        if(ECRI==null){
            ECRI = new EstudianteCarrera_RepositoryImplementacion(emf);
        }
        return ECRI;
    }


    public void anotarEstudianteCarrera(Estudiante estudiante, Carrera carrera, LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            Estudiante est = em.find(Estudiante.class, estudiante.getLU());
            if (est == null) {
                throw new IllegalArgumentException("No se encontró el estudiante con LU: " + estudiante.getLU());
            }
            Carrera car = em.find(Carrera.class, carrera.getIdCarrera());
            if (car == null) {
                throw new IllegalArgumentException("No se encontro la carrera con ID: " + carrera.getIdCarrera());
            }

            EstudianteCarreraID ecId = new EstudianteCarreraID(carrera.getIdCarrera(), estudiante.getLU());
            EstudianteCarrera existeEC = em.find(EstudianteCarrera.class, ecId);
            if (existeEC != null) {
                throw new SQLException("El estudiante ya está matriculado en esta carrera.");
            }

            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(est,car,fechaInicio);
            estudianteCarrera.setFechaFin(fechaFin);
            em.persist(estudianteCarrera);
            et.commit();
        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void matricularEstudiante(Long LU, Long idCarrera) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();

            Estudiante est = em.find(Estudiante.class, LU);
            if (est == null) {
                throw new IllegalArgumentException("No se encontró el estudiante con LU: " + LU);
            }
            Carrera car = em.find(Carrera.class, idCarrera);
            if (car == null) {
                throw new IllegalArgumentException("No se encontro la carrera con ID: " + idCarrera);
            }

            EstudianteCarreraID ecId = new EstudianteCarreraID(idCarrera, LU);
            EstudianteCarrera existeEC = em.find(EstudianteCarrera.class, ecId);
            if (existeEC != null) {
                throw new SQLException("El estudiante ya está matriculado en esta carrera.");
            }


            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(est,car);
            em.persist(estudianteCarrera);
            et.commit();
        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
