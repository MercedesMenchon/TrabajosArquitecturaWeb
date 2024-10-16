package org.example.integrador3.servicio;

import jakarta.transaction.Transactional;
import org.example.integrador3.DTO.EstudianteDTO;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EstudianteServicio{
    @Autowired
    private EstudianteRepository estudianteRepository;

    //a) dar de alta un estudiante
    public Estudiante save(Estudiante estudiante) throws Exception {
        try{
            return estudianteRepository.save(estudiante);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    @Transactional
    public List<EstudianteDTO> findAllEstudiantesDtoOrdenadoPorApellido()throws Exception{

        var resultado = estudianteRepository.findAllEstudiantesDtoOrdenadoPorApellido();
        try{
            return resultado.stream().map(estudiante->new EstudianteDTO(estudiante.getLU(), estudiante.getCiudadResidencia(), estudiante.getGenero(), estudiante.getEdad(), estudiante.getApellido(), estudiante.getNombre(), estudiante.getDNI())).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    public EstudianteDTO findById(Long id) throws Exception {
        try {
            Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);
            if (estudianteOpt.isPresent()) {
                Estudiante estudiante = estudianteOpt.get();
                EstudianteDTO resultado = new EstudianteDTO(estudiante);
                return resultado;
            } else {
                throw new Exception("Estudiante no encontrado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }




    //e) recuperar todos los estudiantes, en base a su género.
    public List<EstudianteDTO> findByGenero(String genero) throws Exception {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findByGenero(genero);
            return estudiantes.stream().map(EstudianteDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public List<EstudianteDTO> findEstudiantesPorCarreraYCiudad(Long idCarrera, String ciudadResidencia) throws Exception {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findEstudiantesPorCarreraYCiudad(idCarrera,ciudadResidencia);
            return estudiantes.stream().map(EstudianteDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }



}

