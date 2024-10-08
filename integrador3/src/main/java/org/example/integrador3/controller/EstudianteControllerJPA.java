package org.example.integrador3.controller;

import org.example.integrador3.model.Estudiante;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("estudiante")

public class EstudianteControllerJPA {
    @Qualifier("estudianteRepository")
    @Autowired
    private final EstuadianteRepository repository;

    public EstudianteControllerJPA(@Qualifier("estudianteRepository")EstudianteRepository repository) {

    }
    @GetMapping("/")
    public Iterable<Estudiante> getEstudiantes() {
        return repository.findAll();
    }

}
