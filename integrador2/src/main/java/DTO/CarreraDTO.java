package main.java.DTO;

import java.util.ArrayList;
import java.util.List;

public class CarreraDTO {

    private Long idCarrera;
    private String nombreCarrera;
    private List<EstudianteCarreraDTO> estudiantes;


    public CarreraDTO(Long idCarrera, String nombreCarrera, List<EstudianteCarreraDTO> estudiantes) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.estudiantes = new ArrayList<EstudianteCarreraDTO>();
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public void addEstudianteCarreraDTO(EstudianteCarreraDTO estudianteCarrea) {
        if(!this.estudiantes.contains(estudianteCarrea)) {
            this.addEstudianteCarreraDTO(estudianteCarrea);
        }
    }

    @Override
    public String toString() {
        return "CarreraDTO{" +
                "idCarrera=" + idCarrera +
                ", nombreCarrera='" + nombreCarrera + '\'' +
                ", estudiantes=" + estudiantes +
                '}';
    }
}
