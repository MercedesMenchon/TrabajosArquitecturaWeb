package org.example.integrador3.Servicio;

import jakarta.transaction.Transactional;
import org.example.integrador3.DTO.EstudianteDTO;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// CAMBIAR LOS RETORNOS A DTO
@Service
public class EstudianteServicio{
    @Autowired
    private EstudianteRepository estudianteRepository;

    //a) dar de alta un estudiante


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


    public Estudiante save(Estudiante estudiante) throws Exception {
        try{
            return estudianteRepository.save(estudiante);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //e) recuperar todos los estudiantes, en base a su género.
//    public EstudianteDTO findByGenero(String genero) throws Exception {
//        try {
//            Optional<Estudiante> estudianteOpt = estudianteRepository.findByGenero(genero);
//            if (estudianteOpt.isPresent()) {
//                return new EstudianteDTO(estudianteOpt.get());
//            } else {
//                throw new Exception("Estudiante no encontrado para el género especificado");
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage(), e);
//        }
//    }

    /*
    * cambie para que traiga una lista y no a un estudiante*/

    public List<EstudianteDTO> findByGenero(String genero) throws Exception {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findByGenero(genero);
            return estudiantes.stream().map(EstudianteDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public List<EstudianteDTO> findAll() throws Exception {
        return null;
    }

    public EstudianteDTO update(Long id, EstudianteDTO entity) throws Exception {
        return null;
    }

    public boolean delete(Long id) throws Exception {
        return false;
    }


}

