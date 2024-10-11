package org.example.integrador3.controller;

import org.example.integrador3.Servicio.CarreraServicio;
import org.example.integrador3.model.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrera")
public class CarreraController{

    @Autowired
    private CarreraServicio carreraServicio;

    @GetMapping("")
    public ResponseEntity<?> getAllCarreras() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al obtener las relaciones entre estudiantes y carreras.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Carrera carrera){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.save(carrera));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

    @GetMapping("/estudiantes-inscriptos")
    public ResponseEntity<?> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }

    }
    // NO ANDA REVISARRRRRRRRRRRR
    @GetMapping("/ID/{id}")
    public ResponseEntity findById(Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró la carrera.\"}");
        }
    }
}
