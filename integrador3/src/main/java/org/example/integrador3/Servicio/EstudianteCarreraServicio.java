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
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    public EstudianteCarrera save(Long idEstudiante, Long idCarrera) throws Exception {
        try {
            // Buscar estudiante y carrera
            Estudiante estudiante = estudianteRepository.findById(idEstudiante)
                    .orElseThrow(() -> new Exception("Estudiante no encontrado"));
            Carrera carrera = carreraRepository.findById(idCarrera)
                    .orElseThrow(() -> new Exception("Carrera no encontrada"));

            EstudianteCarrera existingRelation = estudianteCarreraRepository.findByCarreraIdAndEstudianteId(idCarrera, idEstudiante);
            if (existingRelation != null) {
                throw new Exception("El estudiante ya está matriculado en esta carrera.");
            }

            EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante, carrera);

            return estudianteCarreraRepository.save(estudianteCarrera);
        } catch (Exception e) {
            throw new Exception("Error al guardar la relación Estudiante-Carrera: " + e.getMessage());
        }
    }
}
//    public EstudianteCarrera save(Long idEstudiante, Long idCarrera) throws Exception{
//        try{
//            Estudiante estudiante = estudianteRepository.getEstudiantePorLU(idEstudiante);
//            Carrera carrera = carreraRepository.findCarreraById(idCarrera);
//            if(estudiante != null && carrera != null){
//          //  EstudianteCarreraID idEC = new EstudianteCarreraID(estudiante.getLU(),carrera.getIdCarrera());
////REVISAR QUE NO EXISTA NINGUN ESTUDIANTE MATRICULADO
//
//          //  EstudianteCarrera estudianteCarrera = EstudianteCarreraRepository.findByCarreraIdAndEstudianteId(idEC);
//                //if(controlar que no sea nulo) {
//                    EstudianteCarrera matriculacion = new EstudianteCarrera(estudiante, carrera);
//                    return estudianteCarreraRepository.save(matriculacion);
//                //}
//            }
//            System.out.println("No se matriculo porque el estudiante o la carrera no era nula");
//
//            return null;
//
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }
