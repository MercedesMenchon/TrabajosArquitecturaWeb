package org.example.integrador3.Servicio;


import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.model.EstudianteCarreraID;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


// CAMBIAR LOS RETORNOS A DTO
@Service
public class EstudianteCarreraServicio  {
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;
    private EstudianteRepository estudianteRepository;
    private CarreraRepository carreraRepository;

    public EstudianteCarrera save(Long idEstudiante, Long idCarrera) throws Exception{
        try{
            Estudiante estudiante = estudianteRepository.getEstudiantePorLU(idEstudiante);
            Carrera carrera = carreraRepository.findCarreraById(idCarrera);
            if(estudiante != null && carrera != null){
          //  EstudianteCarreraID idEC = new EstudianteCarreraID(estudiante.getLU(),carrera.getIdCarrera());
//REVISAR QUE NO EXISTA NINGUN ESTUDIANTE MATRICULADO

          //  EstudianteCarrera estudianteCarrera = EstudianteCarreraRepository.findByCarreraIdAndEstudianteId(idEC);
                //if(controlar que no sea nulo) {
                    EstudianteCarrera matriculacion = new EstudianteCarrera(estudiante, carrera);
                    return estudianteCarreraRepository.save(matriculacion);
                //}
            }
            System.out.println("No se matriculo porque el estudiante o la carrera no era nula");

            return null;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }






}
