package org.example.integrador3.servicio;


import jakarta.transaction.Transactional;
import org.example.integrador3.DTO.CarreraDTO;
import org.example.integrador3.DTO.EstudianteCarreraDTO;
import org.example.integrador3.DTO.EstudianteDTO;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.model.EstudianteCarreraID;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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


    @Transactional
    public List<EstudianteCarreraDTO> findAll()throws Exception{

        var resultado = estudianteCarreraRepository.findAll();
        try{
            return resultado.stream().map(estudianteCarrera->new EstudianteCarreraDTO(new EstudianteDTO(estudianteCarrera.getEstudiante()),new CarreraDTO(estudianteCarrera.getCarrera()),estudianteCarrera.getFechaInicio(), estudianteCarrera.getFechaFin())).collect(Collectors.toList());
        }

        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
