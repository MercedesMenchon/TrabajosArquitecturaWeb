package main.java.repositoryImplementaciones;

import main.java.DTO.CarreraDTO;
import main.java.DTO.ReporteDTO;
import main.java.entities.Carrera;
import main.java.repository.CarreraRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CarreraDTO> findCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() {

        String jpql = "SELECT c, COUNT(ec) as inscriptos FROM Carrera c " +
                "JOIN EstudianteCarrera ec ON c.id = ec.carrera.id " +
                "GROUP BY c.id " +
                "HAVING COUNT(ec) > 0 " +
                "ORDER BY inscriptos DESC";

        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> results = query.getResultList();
        List<CarreraDTO> carrerasDTO = new ArrayList<>();

        for (Object[] result : results) {

            Carrera carrera = (Carrera) result[0];
            Long cantidadInscriptos = (Long) result[1];

            CarreraDTO carreraDTO = new CarreraDTO(carrera.getNombreCarrera(), carrera.getIdCarrera(), cantidadInscriptos);
            carrerasDTO.add(carreraDTO);
        }
        return carrerasDTO;
    }

    public List<ReporteDTO> generarReporteCarreras() {
        String jpql = "SELECT c.nombreCarrera, YEAR(ec.fechaInicio), " +
                "COUNT(DISTINCT ec.estudiante) AS inscriptos, " +
                "SUM(CASE WHEN ec.fechaFin IS NOT NULL THEN 1 ELSE 0 END) AS egresados " +
                "FROM Carrera c " +
                "JOIN EstudianteCarrera ec ON c.id = ec.carrera.id " +
                "GROUP BY c.nombreCarrera, YEAR(ec.fechaInicio) " +
                "ORDER BY c.nombreCarrera ASC, YEAR(ec.fechaInicio) ASC";

        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> results = query.getResultList();
        List<ReporteDTO> reporteDTOs = new ArrayList<>();

        // Mapeo de los resultados al DTO
        for (Object[] result : results) {
            String nombreCarrera = (String) result[0];
            int anio = (int) result[1];
            Long cantidadInscriptos = (Long) result[2];
            Long cantidadEgresados = (Long) result[3];

            ReporteDTO reporteDTO = new ReporteDTO(nombreCarrera, anio, cantidadInscriptos, cantidadEgresados);
            reporteDTOs.add(reporteDTO);
        }

        return reporteDTOs;
    }
}

