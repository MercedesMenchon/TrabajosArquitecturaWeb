package main.java.DTO;

public class ReporteDTO {
    private String nombreCarrera;
    private int anio;
    private Long cantidadInscriptos;
    private Long cantidadEgresados;


    public ReporteDTO(String nombreCarrera, int anio, Long cantidadInscriptos, Long cantidadEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.cantidadInscriptos = cantidadInscriptos;
        this.cantidadEgresados = cantidadEgresados;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }


    public int getAnio() {
        return anio;
    }


    public Long getCantidadInscriptos() {
        return cantidadInscriptos;
    }


    public Long getCantidadEgresados() {
        return cantidadEgresados;
    }


    @Override
    public String toString() {
        return "ReporteDTO{" +
                "nombreCarrera='" + nombreCarrera + '\'' +
                ", anio=" + anio +
                ", cantidadInscriptos=" + cantidadInscriptos +
                ", cantidadEgresados=" + cantidadEgresados +
                '}';
    }
}
