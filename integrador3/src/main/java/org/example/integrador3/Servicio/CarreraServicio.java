package org.example.integrador3.Servicio;

import org.example.integrador3.DTO.CarreraDTO;
import org.example.integrador3.DTO.EstudianteDTO;
import org.example.integrador3.model.Carrera;
import org.example.integrador3.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServicio implements BaseService<Carrera> {

    @Autowired
    private CarreraRepository carreraRepository;

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

    public List<CarreraDTO> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() throws Exception {
        List<Carrera[]> results = carreraRepository.getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad();
        List<CarreraDTO> carrerasDTO = new ArrayList<>();

        for (Object[] result : results) {
            Carrera carrera = (Carrera) result[0];
            Long cantidadInscriptos = (Long) result[1];

            CarreraDTO carreraDTO = new CarreraDTO(carrera.getNombreCarrera(), carrera.getIdCarrera(), cantidadInscriptos);
            carrerasDTO.add(carreraDTO);
        }
        try {
            return carrerasDTO;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<?> findAll() throws Exception {
        return null;
    }

    @Override
    public Carrera findCarreraById(Long id) throws Exception {
        return carreraRepository.findCarreraById(id);
    }

    @Override
    public Carrera save(Carrera entity) throws Exception {
        return null;
    }

    @Override
    public Carrera update(Long id, Carrera entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }


}
