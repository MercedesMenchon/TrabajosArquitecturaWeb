package org.example.integrador3.controller;

import org.example.integrador3.DTO.EstudianteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.integrador3.servicio.EstudianteServicio;
import org.example.integrador3.model.Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/LU/{LU}")
    public ResponseEntity<?>getEstudiantePorLU(@PathVariable Long LU){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteServicio.findById(LU));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

//    e) recuperar todos los estudiantes, en base a su género.
    @GetMapping("/genero/{genero}")
    public ResponseEntity<?>getEstudiantesPorGenero(@PathVariable String genero){
        try{
            List<EstudianteDTO> estudiantes = estudianteServicio.findByGenero(genero);
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

//    g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.

    @GetMapping("/carrera-ciudad/{carrera}/{ciudad}")
    public ResponseEntity<?> findEstudiantesPorCarreraYCiudad(
            @PathVariable("carrera") Long idCarrera,
            @PathVariable("ciudad") String ciudad){
        try{
            List<EstudianteDTO> estudiantes = estudianteServicio.findEstudiantesPorCarreraYCiudad(idCarrera,ciudad);
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }
}
