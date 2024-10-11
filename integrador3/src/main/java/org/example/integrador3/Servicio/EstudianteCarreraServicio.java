package org.example.integrador3.Servicio;

import jakarta.transaction.Transactional;
import org.example.integrador3.DTO.EstudianteDTO;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


// CAMBIAR LOS RETORNOS A DTO
@Service
public class EstudianteCarreraServicio  {
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;







    public EstudianteCarrera save(Estudiante estudiante, Carrera carrera) throws Exception{
        try{
            //HACEMOS CHEQUEOS?????????????
            EstudianteCarrera matriculacion = new EstudianteCarrera(estudiante, carrera);
            return estudianteCarreraRepository.save(matriculacion);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }






}
