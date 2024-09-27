package main.java.DTO;

import java.util.Date;

public class EstudianteCarreraDTO {

    private EstudianteCarreraIDDTO estudianteCarreraIDDTO;
    private EstudianteDTO estudianteDTO;
    private CarreraDTO carreraDTO;
    private Date fechaInicio;
    private Date fechaFin;


    public EstudianteCarreraDTO(EstudianteCarreraIDDTO estudianteCarreraIDDTO, CarreraDTO carreraDTO, EstudianteDTO estudianteDTO, Date fechaInicio) {
        this.estudianteCarreraIDDTO = estudianteCarreraIDDTO;
        this.carreraDTO = carreraDTO;
        this.estudianteDTO = estudianteDTO;
        this.fechaInicio = fechaInicio;
        this.fechaFin = null;
    }

    public EstudianteCarreraIDDTO getEstudianteCarreraIDDTO() {
        return estudianteCarreraIDDTO;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public CarreraDTO getCarreraDTO() {
        return carreraDTO;
    }

    public EstudianteDTO getEstudianteDTO() {
        return estudianteDTO;
    }

    @Override
    public String toString() {
        return "EstudianteCarreraDTO{" +
                "estudianteCarreraIDDTO=" + estudianteCarreraIDDTO +
                ", estudianteDTO=" + estudianteDTO +
                ", carreraDTO=" + carreraDTO +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
