package main.java;

import main.java.DTO.CarreraDTO;
import main.java.DTO.EstudianteDTO;
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
        //rp.populateDB();
        Estudiante_RepositoryImplementacion estRepository = rp.getEstudianteRepository();
        Carrera_RepositoryImplementacion carRepository = rp.getCarreraRepository();
        EstudianteCarrera_RepositoryImplementacion estCarRepository = rp.getEstudianteCarreraRepository();


        //a) dar de alta un estudiante

//        Estudiante est = new Estudiante("Karina", "Sanchez", 20L, "Femenino", 24567843L, "Olavarria", 30L);
//        estRepository.insertEstudiante(est);

        //b) matricular un estudiante en una carrera

//        estCarRepository.matricularEstudiante(25L, 2L);

        //c)recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        // El criterio elegido es ordenamiento por apellido
//        for (EstudianteDTO est: estRepository.findAllEstudiantesDTOOrdenadosPorApellido()) {
//            System.out.println(est);
//        }

        //d) recuperar un estudiante, en base a su número de libreta universitaria.

//        System.out.println(estRepository.findEstudianteDTOByLU(1L));

        //e) recuperar todos los estudiantes, en base a su género.
//        for (EstudianteDTO est: estRepository.findEstudiantesDTOByGenero("Femenino")) {
//            System.out.println(est);
//        }

        //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
            for (CarreraDTO car: estRepository.findCarrerasConEstudiantesInscriptosOrdenadasPorCantidad()) {
            System.out.println(car);
        }

        //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.


    }
}
