package org.example.integrador3.DTO;


import org.example.integrador3.model.Carrera;

public class CarreraDTO {

    private Long idCarrera;
    private String nombreCarrera;



    public CarreraDTO(Long idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }


    public CarreraDTO(Carrera carrera){
        this.idCarrera = carrera.getIdCarrera();
        this.nombreCarrera = carrera.getNombreCarrera();
    }

    public Long getIdCarrera() {
        return idCarrera;
    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }



    @Override
    public String toString() {
        return "CarreraDTO{" +
                "idCarrera=" + idCarrera +
                ", nombreCarrera='" + nombreCarrera + '\'' +
                '}';
    }
}
