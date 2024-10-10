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
public class EstudianteServicio implements BaseService<Estudiante>{
    @Autowired
    private EstudianteRepository estudianteRepository;

    //a) dar de alta un estudiante
    @Override
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

    //d) recuperar un estudiante, en base a su número de libreta universitaria.
    @Override
    public Estudiante findById(Long id) throws Exception {
        try{
            Optional<Estudiante> estudiante = estudianteRepository.findById(id);
            return estudiante.get();
            //VER SI HAY QUE RETORNARLO CON DTO
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //e) recuperar todos los estudiantes, en base a su género.
    public Estudiante findByGenero(String genero) throws Exception{
        try{
            Optional<Estudiante> estudiante = estudianteRepository.findByGenero(genero);
            return estudiante.get();
            //VER SI HAY QUE RETORNARLO CON DTO
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<?> findAll() throws Exception {
        return null;
    }

    @Override
    public Estudiante update(Long id, Estudiante entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }


}

