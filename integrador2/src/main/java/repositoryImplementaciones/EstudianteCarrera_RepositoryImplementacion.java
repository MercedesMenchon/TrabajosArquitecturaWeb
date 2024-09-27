package main.java.repositoryImplementaciones;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.entities.EstudianteCarrera;
import main.java.repository.EstudianteCarrera_Repository;

import javax.persistence.*;
import java.util.Date;

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


    //Matricula un estudiante a una carrera
    public void anotarEstudiante(Estudiante estudiante, Carrera carrera, Date fechaInicio) {
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
// REVISAR QUE NO EXISTA EL ESTDIANTE EN LA CARRERA
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
/*
    //Obtiene carreras con estudiantes inscriptos
    public List<EstudiantesCarreraDTO> obtenerCarrerasConEstudiantesInscritos() {
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT new org.example.DTO.EstudiantesCarreraDTO(c.nombre, COUNT(ec.estudiante)) " +
                "FROM Carrera c JOIN Estudiante_Carrera ec ON c.id = ec.carrera.id " +
                "GROUP BY c.id, c.nombre " +
                "ORDER BY COUNT(ec.estudiante) DESC";

        TypedQuery<EstudiantesCarreraDTO> query = em.createQuery(jpql, EstudiantesCarreraDTO.class);
        List<EstudiantesCarreraDTO> resultados = query.getResultList();

        em.close();
        return resultados;
    }

    //Obtiene estudiantes por carrera y ciudad
    public List<EstudianteDTO> obtenerEstudiantesPorCarreraYCiudad(long idCarrera, String ciudad) {
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT e " +
                "FROM Estudiante_Carrera ec JOIN ec.estudiante e " +
                "WHERE ec.carrera.id = :idCarrera AND e.ciudadResidencia = :ciudad";

        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("idCarrera", idCarrera);
        query.setParameter("ciudad", ciudad);

        List<Estudiante> estudiantes = query.getResultList();
        em.close();
        return estudiantes.stream()
                .map(e -> new EstudianteDTO(
                        e.getDni(),
                        e.getNombres(),
                        e.getApellido(),
                        e.getGenero(),
                        e.getCiudadResidencia(),
                        e.getNumeroLibretaUniversitaria()))
                .collect(Collectors.toList());
    }

    //Genera reporte de careras con estudiantes inscriptos y egresados por anio
    public List<ReporteDTO> generarReporteCarreras() {
        EntityManager em = emf.createEntityManager();
        // Obtener las carreras
        String jpqlCarreras = "SELECT c FROM Carrera c ORDER BY c.nombre";
        TypedQuery<Carrera> queryCarreras = em.createQuery(jpqlCarreras, Carrera.class);
        List<Carrera> carreras = queryCarreras.getResultList();

        List<ReporteDTO> reportes = new ArrayList<>();

        for (Carrera carrera : carreras) {
            // Obtener inscriptos por año
            Map<Integer, Long> inscriptosPorAno = em.createQuery(
                            "SELECT YEAR(ec.fechaInscripcion), COUNT(ec) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "WHERE ec.carrera.id = :idCarrera " +
                                    "GROUP BY YEAR(ec.fechaInscripcion)",
                            Object[].class)
                    .setParameter("idCarrera", carrera.getId())
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> (Integer) e[0],
                            e -> (Long) e[1]
                    ));

            Map<Integer, Long> egresadosPorAno = em.createQuery(
                            "SELECT YEAR(ec.fechaInscripcion), COUNT(ec) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "WHERE ec.carrera.id = :idCarrera AND ec.isGraduado = true " +
                                    "GROUP BY YEAR(ec.fechaInscripcion)",
                            Object[].class)
                    .setParameter("idCarrera", carrera.getId())
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> (Integer) e[0],
                            e -> (Long) e[1]
                    ));

            reportes.add(new ReporteDTO(carrera.getNombre(), inscriptosPorAno, egresadosPorAno));
        }

        em.close();
        return reportes;
    }
*/
    // Cierra el EntityManagerFactory al finalizar
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
