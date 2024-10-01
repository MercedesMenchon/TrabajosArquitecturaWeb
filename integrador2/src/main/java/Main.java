package main.java;

import main.java.DTO.CarreraDTO;
import main.java.DTO.EstudianteDTO;
import main.java.DTO.ReporteDTO;
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
       rp.populateDB();
        Estudiante_RepositoryImplementacion estRepository = rp.getEstudianteRepository();
        Carrera_RepositoryImplementacion carRepository = rp.getCarreraRepository();
        EstudianteCarrera_RepositoryImplementacion estCarRepository = rp.getEstudianteCarreraRepository();


        //a) dar de alta un estudiante
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Damos de alta un estudiante:");
        Estudiante estNuevo = new Estudiante("Karina", "Sanchez", 20L, "Femenino", 24567843L, "Olavarria", 30L);
        estRepository.insertEstudiante(estNuevo);

        //b) matricular un estudiante en una carrera
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Matriculamos el estudiante LU:25 en la carrera con id:2 :");
        estCarRepository.matricularEstudiante(30L, 2L);

        //c)recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        // El criterio elegido es ordenamiento por apellido
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Mostramos la lista de estudiantes ordenados por apellido: ");
        for (EstudianteDTO est : estRepository.findAllEstudiantesDTOOrdenadosPorApellido()) {
            System.out.println(est);
        }

        //d) recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Recuperamos un estudiante con número de libreta universitaria 1:");
        System.out.println(estRepository.findEstudianteDTOByLU(1L));

        //e) recuperar todos los estudiantes, en base a su género.
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Recuperamos todos los estudiantes de género femenino:");
        for (EstudianteDTO est : estRepository.findEstudiantesDTOByGenero("Femenino")) {
            System.out.println(est);
        }

        //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Recuperamos las carreras que tienen inscriptos ordenados por la cantidad:");
        for (CarreraDTO car : carRepository.findCarrerasConEstudiantesInscriptosOrdenadasPorCantidad()) {
            System.out.println(car);
        }

        //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Recuperamos estudiantes de una carrera filtrados por ciudad de residencia: ");
        for (EstudianteDTO est : estRepository.findEstudiantesPorCarreraYCiudad(1L, "Buenos Aires")) {
            System.out.println(est);
        }

        // Generar un reporte de las carreras, que para cada carrera incluya información de los
        //inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
        //los años de manera cronológica.
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Generamos un reporte de las carreras:");
        for (ReporteDTO reporteDTO : carRepository.generarReporteCarreras()) {
            System.out.println(reporteDTO);
        }


    }
}
