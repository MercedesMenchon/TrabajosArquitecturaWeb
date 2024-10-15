package org.example.integrador3.DTO;

import java.time.LocalDate;

public class EstudianteCarreraDTO {
private EstudianteDTO estudianteDTO;
private CarreraDTO carreraDTO;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public EstudianteCarreraDTO(EstudianteDTO estudianteDTO,CarreraDTO carreraDTO, LocalDate fechaInicio, LocalDate fechaFin) {
       this.estudianteDTO = estudianteDTO;
       this.carreraDTO = carreraDTO;
       this.fechaInicio = fechaInicio;
       this.fechaFin =fechaFin;
    }

    public EstudianteDTO getEstudianteDTO() {
        return estudianteDTO;
    }

    public void setEstudianteDTO(EstudianteDTO estudianteDTO) {
        this.estudianteDTO = estudianteDTO;
    }

    public CarreraDTO getCarreraDTO() {
        return carreraDTO;
    }

    public void setCarreraDTO(CarreraDTO carreraDTO) {
        this.carreraDTO = carreraDTO;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "EstudianteCarreraDTO{" +
                "estudianteDTO=" + estudianteDTO +
                ", carreraDTO=" + carreraDTO +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
