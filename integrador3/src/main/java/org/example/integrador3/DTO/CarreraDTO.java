package org.example.integrador3.DTO;


public class CarreraDTO {

    private Long idCarrera;
    private Long cantidad;
    private String nombreCarrera;



    public CarreraDTO(Long idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;

    }

    public CarreraDTO(String nombreCarrera, Long idCarrera, Long cantidad){
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.cantidad = cantidad;
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
                ", cantidad inscriptos=" + cantidad +
                '}';
    }
}
