package main.java.factory;


import main.java.repositoryImplementaciones.Carrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.EstudianteCarrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.Estudiante_RepositoryImplementacion;

import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;

public class RepositoryFactoryMySQL extends RepositoryFactory {
    private EntityManagerFactory emf;


    public RepositoryFactoryMySQL() {

        this.emf = Persistence.createEntityManagerFactory("example");
    }

    public Estudiante_RepositoryImplementacion getEstudianteRepository() {
        return new Estudiante_RepositoryImplementacion(emf);
    }

    public Carrera_RepositoryImplementacion getCarreraRepository() {
        return new Carrera_RepositoryImplementacion(emf);
    }

    public EstudianteCarrera_RepositoryImplementacion getEstudianteCarreraRepository() {
        return EstudianteCarrera_RepositoryImplementacion.newEstudianteCarrera_RepositoryImplementacion(emf);
    }

}
