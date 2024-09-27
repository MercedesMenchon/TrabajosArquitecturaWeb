package main.java.factory;

import main.java.repositoryImplementaciones.Carrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.EstudianteCarrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.Estudiante_RepositoryImplementacion;

public class RepositoryFactoryDerby extends RepositoryFactory{


    @Override
    public Estudiante_RepositoryImplementacion getEstudianteRepository() {
        return null;
    }

    @Override
    public Carrera_RepositoryImplementacion getCarreraRepository() {
        return null;
    }

    @Override
    public EstudianteCarrera_RepositoryImplementacion getEstudianteCarreraRepository() {
        return null;
    }
}
