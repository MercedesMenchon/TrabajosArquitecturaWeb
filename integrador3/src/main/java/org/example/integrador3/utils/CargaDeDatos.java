package org.example.integrador3.utils;

import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import springboot.app.modelos.Perro;
import springboot.app.repositorios.PerroRepositorio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*
@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(@Qualifier("estuadianteRepository") EstudianteRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Estudiante((long) 1234,"Seba", "Perez")));
            log.info("Preloading " + repository.save(new Estudiante((long) 2345, "Juan", "Dominguez")));
        };
    }
}

 */

@Component
public class CargaDeDatos {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    @Autowired
    public CargaDeDatos(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository ) {
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "integrador3\\src\\main\\java\\dataSets\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader(header).parse(in);
        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

    public void cargarDesdeCSV() throws IOException {
             for (CSVRecord row : getData("estudiantes.csv")) {
                if (row.size() >= 7) {
                    Long LU = Long.parseLong(row.get(0));
                    Long DNI = Long.parseLong(row.get(1));
                    String nombre = row.get(2);
                    String apellido = row.get(3);
                    Long edad = Long.parseLong(row.get(4));
                    String genero = row.get(5);
                    String ciudad = row.get(6);
                    if (!nombre.isEmpty() && !apellido.isEmpty() && DNI>0 && !ciudad.isEmpty()) {
                        try {

                            Estudiante estudiante = new Estudiante(nombre,apellido,edad,genero,DNI,ciudad,LU);

                            estudianteRepository.save(estudiante); // Guarda el estudiante en la base de datos
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de estudiantes: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Estudiantes insertados");

            for (CSVRecord fila : getData("carreras.csv")) {
                if (fila.size() >= 2) {
                    Long id = Long.parseLong(fila.get(0));
                    String nombre = fila.get(1);

                    System.out.println(id);
                    System.out.println(nombre);
                    if (!nombre.isEmpty() && id >= 0) {
                        try {
                            Carrera carrera = new Carrera(id,nombre);
                            carreraRepository.save(carrera);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de carrera: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Carreras insertadas");

            for (CSVRecord row : getData("estudianteCarrera.csv")) {
                if (row.size() >= 4) {
                    Long LU = Long.parseLong(row.get(0));
                    Long idCarrera = Long.parseLong(row.get(1));
                    String fechaInicioStr = row.get(2);
                    LocalDate fechaInicio = fechaInicioStr.isEmpty() ? null : LocalDate.parse(fechaInicioStr);
                    String fechaFinStr = row.get(3);
                    LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr);

                    try {
                        Estudiante estudiante = estudianteRepository.getEstudiantePorLU(LU);
                        Carrera carrera = carreraRepository.findCarreraById(idCarrera);

                        if (estudiante != null && carrera != null) {

                            EstudianteCarreraRepository estudianteCarreraRepository=getEstudianteCarreraRepository();
                            estudianteCarreraRepository.anotarEstudianteCarrera(estudiante, carrera, fechaInicio, fechaFin);
                        }
                    } catch (Exception e) {
                        System.err.println("Error al insertar EstudianteCarrera: " + e.getMessage());
                    }
                }
            }
            System.out.println("EstudianteCarrera insertados");
        }


}