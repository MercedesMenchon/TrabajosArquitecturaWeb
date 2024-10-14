package org.example.integrador3.controller;

import org.example.integrador3.Servicio.EstudianteCarreraServicio;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("estudianteCarrera")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraServicio estudianteCarreraServicio;



    //b) matricular un estudiante en una carrera

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Map<String, Long> request) {
        try {
            Long idEstudiante = request.get("idEstudiante");
            Long idCarrera = request.get("idCarrera");
            // Verifica si los valores no son nulos o están correctamente recibidos
            if (idEstudiante == null || idCarrera == null) {
                throw new IllegalArgumentException("idEstudiante o idCarrera no proporcionados.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudianteCarreraServicio.save(idEstudiante, idCarrera));
        } catch (Exception e) {
            // Aquí registramos el error para obtener más detalles.
            e.printStackTrace(); // Para ver la traza del error en la consola/logs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente. Detalle: " + e.getMessage() + "\"}");
        }
    }





}
