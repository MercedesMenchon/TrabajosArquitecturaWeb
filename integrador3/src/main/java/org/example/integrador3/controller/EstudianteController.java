package org.example.integrador3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.example.integrador3.Servicio.EstudianteServicio;
import org.example.integrador3.model.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteServicio estudianteServicio;

    //a) dar de alta un estudiante
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Estudiante estudiante){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteServicio.save(estudiante));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    @GetMapping("")
    public ResponseEntity<?> getEstudiantesOrderByApellido() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteServicio.findAllEstudiantesDtoOrdenadoPorApellido());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }

    }

    //d) recuperar un estudiante, en base a su número de libreta universitaria.
    @GetMapping("/{LU}")
    public ResponseEntity<?>getEstudiantePorLU(@PathVariable Long LU){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteServicio.findById(LU));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

    //e) recuperar todos los estudiantes, en base a su género.
    @GetMapping("/{genero}")
    public ResponseEntity<?>getEstudiantesPorGenero(@PathVariable String genero){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteServicio.findByGenero(genero));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }
}
