package org.example.integrador3.servicio;

import jakarta.transaction.Transactional;

import org.example.integrador3.DTO.CarreraConInscriptosDTO;
import org.example.integrador3.DTO.CarreraDTO;
import org.example.integrador3.DTO.ReporteDTO;
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

    public List<CarreraConInscriptosDTO> getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad() throws Exception {

        List<Object[]> results = carreraRepository.getCarrerasConEstudiantesInscriptosOrdenadasPorCantidad();
        List<CarreraConInscriptosDTO> listaCarrerasDTO = new ArrayList<>();

        for (Object[] result : results) {
            Carrera carrera = (Carrera) result[0];
            Long cantidadInscriptos = (Long) result[1];

            CarreraConInscriptosDTO carreraDTO = new CarreraConInscriptosDTO(carrera.getNombreCarrera(), carrera.getIdCarrera(),cantidadInscriptos);
            listaCarrerasDTO.add(carreraDTO);
        }
        try {
            return listaCarrerasDTO;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public CarreraDTO findById(Long id) throws Exception {
        Carrera carrera = carreraRepository.findCarreraById(id);
        if (carrera != null) {
            return new CarreraDTO(carrera.getIdCarrera(), carrera.getNombreCarrera());
        } else {
            throw new Exception("No se encontró la carrera con el ID: " + id);
        }
    }

    public Carrera save(Carrera carrera) throws Exception {
        try{
          return carreraRepository.save(carrera);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public List<ReporteDTO> getReporte() throws Exception {
        try {
            return carreraRepository.getReporte();
        } catch (Exception e) {
          throw new Exception("Error al obtener el reporte: " + e.getMessage());
        }
    }
}
