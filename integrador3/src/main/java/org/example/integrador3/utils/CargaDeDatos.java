package org.example.integrador3.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class CargaDeDatos implements CommandLineRunner {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    private final EstudianteCarreraRepository estudianteCarreraRepository;

    @Autowired
    public CargaDeDatos(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository, EstudianteCarreraRepository estudianteCarreraRepository) {
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
        this.estudianteCarreraRepository = estudianteCarreraRepository;
    }

    @Override
    public void run(String... args) {
        cargarDesdeCSV();
        log.info("Datos cargados desde CSV correctamente.");
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        ClassPathResource resource = new ClassPathResource("dataSets/" + archivo);
        if (!resource.exists()) {
            throw new IOException("Archivo CSV no encontrado: " + archivo);
        }
        try (Reader in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in).getRecords();
        }
    }

    public void cargarDesdeCSV() {
                try {
            // Carga estudiantes
            log.info("Cargando estudiantes...");
            for (CSVRecord row : getData("estudiantes.csv")) {
               if (row.size() >= 7) {

                    try {
                        Long LU = parseLong(row.get("LU"),"LU");
                        System.out.println("estoy aca*************//////////////////");
                        System.out.println(LU);
                        Long DNI = parseLong(row.get("DNI"),"DNI");
                        String nombre = row.get("Nombre").trim();
                        String apellido = row.get("Apellido").trim();
                        Long edad = parseLong(row.get("Edad"),"Edad");
                        String genero = row.get("Genero").trim();
                        String ciudad = row.get("CiudadResidencia").trim();

                        if (!nombre.isEmpty() && !apellido.isEmpty() && DNI > 0 && !ciudad.isEmpty()) {
                            Estudiante estudiante = new Estudiante(nombre,apellido,edad,genero,DNI,ciudad,LU);
                            estudianteRepository.save(estudiante);
                            log.debug("Estudiante guardado: {}", estudiante);
                        }
                    } catch (NumberFormatException e) {
                        log.error("Error de formato en datos de estudiantes: {}", e.getMessage());
                    }
                } else {
                    log.warn("Registro de estudiante con columnas insuficientes: {}", row);
                }
            }
            log.info("Estudiantes insertados");

            // Carga carreras
            log.info("Cargando carreras...");
            for (CSVRecord fila : getData("carreras.csv")) {
                if (fila.size() >= 2) {
                    try {
                        Long id = parseLong(fila.get("ID de carrera"), "ID de carrera");
                        String nombre = fila.get("Nombre").trim();

                        if (!nombre.isEmpty() && id >= 0) {
                            Carrera carrera = new Carrera(id, nombre);
                            carreraRepository.save(carrera);
                            log.debug("Carrera guardada: {}", carrera);
                        }
                    } catch (NumberFormatException e) {
                        log.error("Error de formato en datos de carrera: {}", e.getMessage());
                    }
                } else {
                    log.warn("Registro de carrera con columnas insuficientes: {}", fila);
                }
            }
            log.info("Carreras insertadas");

            // Carga estudianteCarrera
            log.info("Cargando estudianteCarrera...");
            for (CSVRecord row : getData("estudianteCarrera.csv")) {
                if (row.size() >= 4) {
                    try {
                        Long LU = parseLong(row.get("LU"), "LU");
                        Long idCarrera = parseLong(row.get("ID de carrera"), "ID de carrera");
                        LocalDate fechaInicio = parseLocalDate(row.get("Fecha de inicio"), "Fecha de inicio");
                        LocalDate fechaFin = parseLocalDate(row.get("Fecha de fin"), "Fecha de fin");

                        Estudiante estudiante = estudianteRepository.getEstudiantePorLU(LU);
                        Carrera carrera = carreraRepository.findCarreraById(idCarrera);
                        if (estudiante != null && carrera != null) {
                            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, fechaInicio);
                            estudianteCarreraRepository.save(estudianteCarrera);
                            log.debug("EstudianteCarrera guardado: {}", estudianteCarrera);
                        } else {
                            log.warn("Estudiante o Carrera no encontrados para LU: {} y ID carrera: {}", LU, idCarrera);
                        }
                    } catch (Exception e) {
                        log.error("Error al insertar EstudianteCarrera: {}", e.getMessage());
                    }
                } else {
                    log.warn("Registro de estudianteCarrera con columnas insuficientes: {}", row);
                }
            }
            log.info("EstudianteCarrera insertados");
        } catch (IOException e) {
            log.error("Error al cargar datos desde CSV: {}", e.getMessage());
        }
    }

    private Long parseLong(String value, String fieldName) throws NumberFormatException {
        if (value.trim().isEmpty()) {
            throw new NumberFormatException(fieldName + " está vacío");
        }
        return Long.parseLong(value);
    }

    private LocalDate parseLocalDate(String value, String fieldName) {
        if (value.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta según tu formato
        try {
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error en el formato de fecha para {}: {}", fieldName, e.getMessage());
            return null; // Manejar según sea necesario, aquí retorna null si hay un error
        }
    }
}





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

/*@Component
public class CargaDeDatos {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    private final EstudianteCarreraRepository estudianteCarreraRepository;
    @Autowired
    public CargaDeDatos(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository, EstudianteCarreraRepository estudianteCarreraRepository) {
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
        this.estudianteCarreraRepository = estudianteCarreraRepository;
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
                            estudianteCarreraRepository.matricularEstudianteEnCarrera(estudiante, carrera, fechaInicio, fechaFin);
                        }
                    } catch (Exception e) {
                        System.err.println("Error al insertar EstudianteCarrera: " + e.getMessage());
                    }
                }
            }
            System.out.println("EstudianteCarrera insertados");
        }
}

*/


//@Component
//public class CargaDeDatos implements CommandLineRunner {
//
//    private final EstudianteRepository estudianteRepository;
//    private final CarreraRepository carreraRepository;
//    private final EstudianteCarreraRepository estudianteCarreraRepository;
//
//    @Autowired
//    public CargaDeDatos(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository, EstudianteCarreraRepository estudianteCarreraRepository) {
//        this.estudianteRepository = estudianteRepository;
//        this.carreraRepository = carreraRepository;
//        this.estudianteCarreraRepository = estudianteCarreraRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        cargarDesdeCSV();
//        System.out.println("ENtrOOOOOOOOO-----------------------------------------------------------------------------------");
//    }
//
//
//
//    private Iterable<CSVRecord> getData(String archivo) throws IOException {
//        // Cambiar a una ruta relativa o absoluta según sea necesario
//        String path = Paths.get("integrador3/src/main/java/org.example.integrador3/dataSets/", archivo).toString();
//        System.out.println("Ruta del archivo CSV: " + path);
//
//        try (Reader in = new FileReader(path)) {
//            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in).getRecords();
//            System.out.println("Archivo leído correctamente");
//            return records;
//        } catch (FileNotFoundException e) {
//            System.err.println("El archivo no se encuentra: " + e.getMessage());
//        } catch (IOException e) {
//            System.err.println("Error al leer el archivo: " + e.getMessage());
//        }
//        Reader in = new FileReader(path);
//        // Suponiendo que los CSV tienen encabezados, si no, puedes usar .withSkipHeaderRecord()
//        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in).getRecords();
//    }
//
//    public void cargarDesdeCSV() {
//        try {
//            // Carga estudiantes
//            System.out.println("Cargando estudiantes...");
//            for (CSVRecord row : getData("estudiantes.csv")) {
//                System.out.println("entre al csv-------------------------------------------------23123123123123213123123213");
//                if (row.size() >= 7) {
//                    try {
//                        Long LU = parseLong(row.get(0), "LU");
//                        Long DNI = parseLong(row.get(1), "DNI");
//                        String nombre = row.get(2).trim();
//                        String apellido = row.get(3).trim();
//                        Long edad = parseLong(row.get(4), "Edad");
//                        String genero = row.get(5).trim();
//                        String ciudad = row.get(6).trim();
//
//                        if (!nombre.isEmpty() && !apellido.isEmpty() && DNI > 0 && !ciudad.isEmpty()) {
//                            Estudiante estudiante = new Estudiante(nombre, apellido, edad, genero, DNI, ciudad, LU);
//                            estudianteRepository.save(estudiante);
//                        }
//                    } catch (NumberFormatException e) {
//                        System.err.println("Error de formato en datos de estudiantes: " + e.getMessage());
//                    }
//                }
//            }
//            System.out.println("Estudiantes insertados");
//
//            // Carga carreras
//            System.out.println("Cargando carreras...");
//            for (CSVRecord fila : getData("carreras.csv")) {
//                if (fila.size() >= 2) {
//                    try {
//                        Long id = parseLong(fila.get(0), "ID de carrera");
//                        String nombre = fila.get(1).trim();
//
//                        if (!nombre.isEmpty() && id >= 0) {
//                            Carrera carrera = new Carrera(id, nombre);
//                            carreraRepository.save(carrera);
//                        }
//                    } catch (NumberFormatException e) {
//                        System.err.println("Error de formato en datos de carrera: " + e.getMessage());
//                    }
//                }
//            }
//            System.out.println("Carreras insertadas");
//
//            // Carga estudianteCarrera
//            System.out.println("Cargando estudianteCarrera...");
//            for (CSVRecord row : getData("estudianteCarrera.csv")) {
//                if (row.size() >= 4) {
//                    try {
//                        Long LU = parseLong(row.get(0), "LU");
//                        Long idCarrera = parseLong(row.get(1), "ID de carrera");
//                        LocalDate fechaInicio = parseLocalDate(row.get(2), "Fecha de inicio");
//                        LocalDate fechaFin = parseLocalDate(row.get(3), "Fecha de fin");
//
//                        Estudiante estudiante = estudianteRepository.getEstudiantePorLU(LU);
//                        Carrera carrera = carreraRepository.findCarreraById(idCarrera);
//                        if (estudiante != null && carrera != null) {
//                            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, fechaInicio);
//                            estudianteCarreraRepository.save(estudianteCarrera);
//                        }
//                    } catch (Exception e) {
//                        System.err.println("Error al insertar EstudianteCarrera: " + e.getMessage());
//                    }
//                }
//            }
//            System.out.println("EstudianteCarrera insertados");
//        } catch (IOException e) {
//            System.err.println("Error al cargar datos desde CSV: " + e.getMessage());
//        }
//    }
//
//    private Long parseLong(String value, String fieldName) throws NumberFormatException {
//        if (value.trim().isEmpty()) {
//            throw new NumberFormatException(fieldName + " está vacío");
//        }
//        return Long.parseLong(value);
//    }
//
//    private LocalDate parseLocalDate(String value, String fieldName) {
//        if (value.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            return LocalDate.parse(value);
//        } catch (DateTimeParseException e) {
//            System.err.println("Error en el formato de fecha para " + fieldName + ": " + e.getMessage());
//            return null; // Manejar según sea necesario, aquí retorna null si hay un error
//        }
//    }
//}


//    private Iterable<CSVRecord> getData(String archivo) throws IOException {
//        String path = "integrador3/src/main/java/org.example.integrador3/dataSets/" + archivo;
//        Reader in = new FileReader(path);
//        String[] header = {};
//        CSVParser csvParser = CSVFormat.DEFAULT.withHeader(header).parse(in);
//        return csvParser.getRecords();
//    }
//
//    public void cargarDesdeCSV() throws IOException {
//        // Carga estudiantes
//        System.out.println("ENTREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE-----------------------------------------------------------------------------------");
//        for (CSVRecord row : getData("estudiantes.csv")) {
//            if (row.size() >= 7) {
//                Long LU = Long.parseLong(row.get(0));
//                Long DNI = Long.parseLong(row.get(1));
//                String nombre = row.get(2);
//                String apellido = row.get(3);
//                Long edad = Long.parseLong(row.get(4));
//                String genero = row.get(5);
//                String ciudad = row.get(6);
//                if (!nombre.isEmpty() && !apellido.isEmpty() && DNI > 0 && !ciudad.isEmpty()) {
//                    try {
//                        Estudiante estudiante = new Estudiante(nombre, apellido, edad, genero, DNI, ciudad, LU);
//                        estudianteRepository.save(estudiante);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Error de formato en datos de estudiantes: " + e.getMessage());
//                    }
//                }
//            }
//        }
//        System.out.println("Estudiantes insertados");
//
//        // Carga carreras
//        for (CSVRecord fila : getData("carreras.csv")) {
//            if (fila.size() >= 2) {
//                Long id = Long.parseLong(fila.get(0));
//                String nombre = fila.get(1);
//                if (!nombre.isEmpty() && id >= 0) {
//                    try {
//                        Carrera carrera = new Carrera(id, nombre);
//                        carreraRepository.save(carrera);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Error de formato en datos de carrera: " + e.getMessage());
//                    }
//                }
//            }
//        }
//        System.out.println("Carreras insertadas");
//
//        // Carga estudianteCarrera
//        for (CSVRecord row : getData("estudianteCarrera.csv")) {
//            if (row.size() >= 4) {
//                Long LU = Long.parseLong(row.get(0));
//                Long idCarrera = Long.parseLong(row.get(1));
//                String fechaInicioStr = row.get(2);
//                LocalDate fechaInicio = fechaInicioStr.isEmpty() ? null : LocalDate.parse(fechaInicioStr);
//                String fechaFinStr = row.get(3);
//                LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr);
//                try {
//                    Estudiante estudiante = estudianteRepository.getEstudiantePorLU(LU);
//                    Carrera carrera = carreraRepository.findCarreraById(idCarrera);
//                    if (estudiante != null && carrera != null) {
//                        EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, fechaInicio);
//                        estudianteCarreraRepository.save(estudianteCarrera);
//                    }
//                } catch (Exception e) {
//                    System.err.println("Error al insertar EstudianteCarrera: " + e.getMessage());
//                }
//            }
//        }
//        System.out.println("EstudianteCarrera insertados");
//    }