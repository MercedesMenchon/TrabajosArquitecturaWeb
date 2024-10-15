package org.example.integrador3.DTO;


public class CarreraConInscriptosDTO extends CarreraDTO{
    private Long cantidad;

    public CarreraConInscriptosDTO(String nombreCarrera, Long idCarrera, Long cantidad){
        super(idCarrera, nombreCarrera);
        this.cantidad= cantidad;
    }

    @Override
    public String toString() {
        return "CarreraDTO{" +
                "idCarrera=" + super.getIdCarrera() +
                ", nombreCarrera='" + super.getNombreCarrera() + '\'' +
                ", cantidad inscriptos=" + cantidad +
                '}';
    }
}
