package org.example.integrador3.controller;


import org.example.integrador3.Servicio.EstudianteCarreraServicio;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/estudianteCarrera")
public class EstudianteCarreraController {
    @Autowired
    private EstudianteCarreraServicio estudianteCarreraServicio;



    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Map<String, Long> request) {
        try {
            Long idEstudiante = request.get("idEstudiante");
            Long idCarrera = request.get("idCarrera");

            if (idEstudiante == null || idCarrera == null) {
                throw new IllegalArgumentException("idEstudiante o idCarrera no proporcionados.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudianteCarreraServicio.save(idEstudiante, idCarrera));
        } catch (Exception e) {
             e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente. Detalle: " + e.getMessage() + "\"}");
        }
    }
}

