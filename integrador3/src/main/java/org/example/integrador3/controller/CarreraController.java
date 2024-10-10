package org.example.integrador3.controller;

import org.example.integrador3.Servicio.CarreraServicio;
import org.example.integrador3.Servicio.EstudianteServicio;
import org.example.integrador3.model.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrera")
public class CarreraController{

    @Autowired
    private CarreraServicio carreraServicio;

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

    @GetMapping("/estudiantes-inscriptos")
    public ResponseEntity<?> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carreraServicio.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró la carrera.\"}");
        }
    }
}
