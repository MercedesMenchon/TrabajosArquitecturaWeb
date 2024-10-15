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


    public CarreraDTO getCarreraDTO() {
        return carreraDTO;
    }


    public LocalDate getFechaInicio() {
        return fechaInicio;
    }


    public LocalDate getFechaFin() {
        return fechaFin;
    }



    @Override
    public String toString() {
        return "EstudianteCarreraDTO{" +
                "estudianteDTO=" + getEstudianteDTO() +
                ", carreraDTO=" + getCarreraDTO() +
                ", fechaInicio=" + getFechaInicio() +
                ", fechaFin=" + getFechaFin() +
                '}';
    }
}
