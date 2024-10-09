package org.example.integrador3.Servicio;

import org.example.integrador3.model.Carrera;
import org.example.integrador3.model.Estudiante;
import org.example.integrador3.model.EstudianteCarrera;
import org.example.integrador3.repository.CarreraRepository;
import org.example.integrador3.repository.EstudianteCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstudianteCarreraServicio implements BaseService<EstudianteCarrera> {
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;

    public EstudianteCarrera matricularEstudianteEnCarrera(Estudiante estudiante, Carrera carrera) throws Exception{
        try{
            //HACEMOS CHEQUEOS?????????????
            EstudianteCarrera matriculacion = new EstudianteCarrera(estudiante, carrera);
            return estudianteCarreraRepository.save(matriculacion);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public List<?> findAll() throws Exception {
        return null;
    }

    @Override
    public EstudianteCarrera findById(Long id) throws Exception {
        return null;
    }

    @Override
    public EstudianteCarrera save(EstudianteCarrera entity) throws Exception {
        return null;
    }

    @Override
    public EstudianteCarrera update(Long id, EstudianteCarrera entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }


}
