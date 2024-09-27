package main.java;

import main.java.factory.RepositoryFactory;
import main.java.repositoryImplementaciones.Carrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.EstudianteCarrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.Estudiante_RepositoryImplementacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {

    public static void main(String[] args) throws Exception {


        RepositoryFactory rp = RepositoryFactory.getRepositoryFactory(1);
        rp.populateDB();
       Estudiante_RepositoryImplementacion estRepository = rp.getEstudianteRepository();
       Carrera_RepositoryImplementacion carRepository = rp.getCarreraRepository();
       EstudianteCarrera_RepositoryImplementacion estCarRepository = rp.getEstudianteCarreraRepository();

        // Cargar Estudiantes desde CSV
       // cargarEstudiantesDesdeCSV(estudianteRepository, "src/main/java/org/example/datasets/estudiantes.csv");
        // Cargar Carreras desde CSV
        //cargarCarrerasDesdeCSV(carreraRepository, "src/main/java/org/example/datasets/carreras.csv");
    }
}
