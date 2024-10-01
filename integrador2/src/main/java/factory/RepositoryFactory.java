package main.java.factory;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.repository.CarreraRepository;
import main.java.repository.EstudianteCarrera_Repository;
import main.java.repository.EstudianteRepository;
import main.java.repositoryImplementaciones.Carrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.EstudianteCarrera_RepositoryImplementacion;
import main.java.repositoryImplementaciones.Estudiante_RepositoryImplementacion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public abstract class RepositoryFactory {

    private Connection conn = null;

    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;


    public abstract Estudiante_RepositoryImplementacion getEstudianteRepository();

    public abstract Carrera_RepositoryImplementacion getCarreraRepository();

    public abstract EstudianteCarrera_RepositoryImplementacion getEstudianteCarreraRepository();


    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC:
                return new RepositoryFactoryMySQL();
            case DERBY_JDBC:
                return new RepositoryFactoryDerby();
            default:
                return null;
        }


    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "integrador2\\src\\main\\java\\dataSets\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader(header).parse(in);
        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }


    public void populateDB() throws Exception, SQLException {
        System.out.println("Populating DB...");
        EstudianteRepository estudianteRepository = getEstudianteRepository();
        CarreraRepository carreraRepository = getCarreraRepository();
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

                        estudianteRepository.insertEstudiante(estudiante);
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

                        carreraRepository.insertCarrera(carrera);
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

                        EstudianteCarrera_Repository estudianteCarreraRepository=getEstudianteCarreraRepository();
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










