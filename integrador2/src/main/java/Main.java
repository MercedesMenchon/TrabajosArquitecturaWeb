package main.java;

import main.java.entities.Estudiante;
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
    //    rp.populateDB();
        Estudiante_RepositoryImplementacion estRepository = rp.getEstudianteRepository();
        Carrera_RepositoryImplementacion carRepository = rp.getCarreraRepository();
        EstudianteCarrera_RepositoryImplementacion estCarRepository = rp.getEstudianteCarreraRepository();


        //a) dar de alta un estudiante

       // Estudiante est = new Estudiante("Karina", "Sanchez", 20, "Femenino", 24567843, "Olavarria", 30L);
         //estRepository.insertEstudiante(est);

        //b) matricular un estudiante en una carrera

       // estCarRepository.matricularEstudiante(25L, 2L);

        //c)recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.


        System.out.println(estRepository.findAllEstudiantesDTOOrdenadosPorApellido());


        //d) recuperar un estudiante, en base a su número de libreta universitaria.
        //e) recuperar todos los estudiantes, en base a su género.
        //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.


    }
}
