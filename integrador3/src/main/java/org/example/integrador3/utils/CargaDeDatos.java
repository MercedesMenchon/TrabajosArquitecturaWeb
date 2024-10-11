package org.example.integrador3.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
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

    public void cargarDesdeCSV() {
    this.cargarEstudiantesCSV();
    this.cargarCarrerasCSV();
    this.cargarEstudianteCarreraCSV();
    }

    // Método para cargar datos desde un archivo CSV
    public void cargarEstudiantesCSV() {
        try {
            // Obtén el archivo CSV utilizando ResourceUtils.getFile()
            File archivoCSV = ResourceUtils.getFile("classpath:dataSets/estudiantes.csv");
            try (FileReader reader = new FileReader(archivoCSV);
                 CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

                // Cargar estudiantes desde el archivo CSV
                log.info("Cargando estudiantes...");
                for (CSVRecord row : csvParser) {
                    if (row.size() >= 7) {
                        try {
                            Long LU = Long.parseLong(row.get(0));
                            Long DNI = Long.parseLong(row.get(1));
                            String nombre = row.get(2);
                            String apellido = row.get(3);
                            Long edad = Long.parseLong(row.get(4));
                            String genero = row.get(5);
                            String ciudad = row.get(6);

                            // Valida y guarda el estudiante
                            if (!nombre.isEmpty() && !apellido.isEmpty() && DNI > 0 && !ciudad.isEmpty()) {
                                Estudiante estudiante = new Estudiante(nombre, apellido, edad, genero, DNI, ciudad, LU);
                                estudianteRepository.save(estudiante);
                                System.out.println("ESTOY CARGAND ESTUDIANTEEEEEEEEES" );
                                log.debug("Estudiante guardado: {}", estudiante);
                            }
                        } catch (NumberFormatException e) {
                            log.error("Error de formato en datos de estudiantes: {}", e.getMessage());
                        }
                    } else {
                        log.warn("Registro de estudiante con columnas insuficientes: {}", row);
                    }
                }
                log.info("Estudiantes insertados correctamente.");
            }
        } catch (IOException e) {
            log.error("Error al cargar estudiantes desde CSV: {}", e.getMessage());
        }
    }


    public void cargarCarrerasCSV() {
        try {
            // Obtén el archivo CSV utilizando ResourceUtils.getFile()
            File archivoCSV = ResourceUtils.getFile("classpath:dataSets/carreras.csv");
            try (FileReader reader = new FileReader(archivoCSV);
                 CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

                // Cargar carreras desde el archivo CSV
                log.info("Cargando carreras...");
                for (CSVRecord fila : csvParser) {
                    if (fila.size() >= 2) {
                        try {
                            Long id = Long.parseLong(fila.get(0));
                            String nombre = fila.get(1);

                            // Valida y guarda la carrera
                            if (id > 0 && !nombre.isEmpty()) {
                                Carrera carrera = new Carrera(id, nombre);
                                carreraRepository.save(carrera);
                                log.debug("Carrera guardada: {}", carrera);
                            }
                        } catch (NumberFormatException e) {
                            log.error("Error de formato en datos de carreras: {}", e.getMessage());
                        }
                    } else {
                        log.warn("Registro de carrera con columnas insuficientes: {}", fila);
                    }
                }
                log.info("Carreras insertadas correctamente.");
            }
        } catch (IOException e) {
            log.error("Error al cargar carreras desde CSV: {}", e.getMessage());
        }
    }

    public void cargarEstudianteCarreraCSV() {
        try {
            // Obtén el archivo CSV utilizando ResourceUtils.getFile()
            File archivoCSV = ResourceUtils.getFile("classpath:dataSets/estudianteCarrera.csv");
            try (FileReader reader = new FileReader(archivoCSV);
                 CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

                log.info("Cargando relaciones estudiante-carrera...");
                for (CSVRecord row : csvParser) {
                    if (row.size() >= 4) {
                        try {
                            Long LU = Long.parseLong(row.get(0)); // LU del estudiante
                            Long idCarrera = Long.parseLong(row.get(1)); // ID de la carrera
                            String fechaInicioStr = row.get(2); // Fecha de inicio
                            LocalDate fechaInicio = fechaInicioStr.isEmpty() ? null : LocalDate.parse(fechaInicioStr);
                            String fechaFinStr = row.get(3); // Fecha de fin
                            LocalDate fechaFin = fechaFinStr.isEmpty() ? null : LocalDate.parse(fechaFinStr);

                            // Obtener el estudiante y la carrera correspondientes
                            Estudiante estudiante = estudianteRepository.getEstudiantePorLU(LU);
                            Carrera carrera = carreraRepository.findCarreraById(idCarrera);

                            // Validar que ambos existan
                            if (estudiante != null && carrera != null) {
                                // Crear la relación estudiante-carrera
                                EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera, fechaInicio);
                                estudianteCarrera.setFechaFin(fechaFin);
                                estudianteCarreraRepository.save(estudianteCarrera);
                                log.debug("Relación Estudiante-Carrera guardada: {}", estudianteCarrera);
                            } else {
                                if (estudiante == null) {
                                    log.warn("No se encontró el estudiante con LU: {}", LU);
                                }
                                if (carrera == null) {
                                    log.warn("No se encontró la carrera con ID: {}", idCarrera);
                                }
                            }
                        } catch (NumberFormatException e) {
                            log.error("Error de formato en datos de estudiante-carrera: {}", e.getMessage());
                        } catch (DateTimeParseException e) {
                            log.error("Error al parsear fechas: {}", e.getMessage());
                        }
                    } else {
                        log.warn("Registro de estudiante-carrera con columnas insuficientes: {}", row);
                    }
                }
                log.info("Relaciones estudiante-carrera insertadas correctamente.");
            }
        } catch (IOException e) {
            log.error("Error al cargar relaciones estudiante-carrera desde CSV: {}", e.getMessage());
        }
    }




}