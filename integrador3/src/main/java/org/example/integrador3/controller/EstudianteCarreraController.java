package org.example.integrador3.controller;

import org.example.integrador3.Servicio.EstudianteCarreraServicio;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estudianteCarrera")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraServicio estudianteCarreraServicio;

    //b) matricular un estudiante en una carrera
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Estudiante estudiante, Carrera carrera){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteCarreraServicio.save(estudiante, carrera));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

}
