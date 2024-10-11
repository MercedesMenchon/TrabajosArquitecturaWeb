package org.example.integrador3.Servicio;

import jakarta.transaction.Transactional;

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
public class CarreraServicio  {

    @Autowired
    private CarreraRepository carreraRepository;





    @Transactional
    public List<CarreraDTO> findAll() throws Exception {
        var resultado = carreraRepository.findAll();
        try {
            return resultado.stream()
                    .map(carrera -> new CarreraDTO(carrera.getIdCarrera(), carrera.getNombreCarrera()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

    public List<CarreraDTO> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() throws Exception {

        List<Object[]> results = carreraRepository.getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad();

        System.out.println("++++++++++++++++++" + results);
        List<CarreraDTO> carrerasDTO = new ArrayList<>();

        for (Object[] result : results) {
            System.out.println("ENTRE 222222222222222222222222222222222222");
            Carrera carrera = (Carrera) result[0];
            Long cantidadInscriptos = (Long) result[1];

            CarreraDTO carreraDTO = new CarreraDTO(carrera.getNombreCarrera(), carrera.getIdCarrera(),cantidadInscriptos);
            carrerasDTO.add(carreraDTO);
        }
        try {
            return carrerasDTO;
        }
        catch (Exception e){
            System.out.println("ENTRE 888888888888888888888888888888888888888");
            throw new Exception(e.getMessage());
        }
    }



    public CarreraDTO findById(Long id) throws Exception {
        Carrera resultado = carreraRepository.findCarreraById(id);
        CarreraDTO carreraDTO = null;
        if (resultado != null){
            carreraDTO = new CarreraDTO(resultado.getIdCarrera(),resultado.getNombreCarrera());
        }
        return carreraDTO;
    }



    public Carrera save(Carrera carrera) throws Exception {
        try{
          return carreraRepository.save(carrera);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }





}
