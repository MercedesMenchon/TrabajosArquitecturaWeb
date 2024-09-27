package main.java.DTO;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDTO {
    private Long LU;
    private Long DNI;
    private String nombre;
    private String apellido;
    private Long edad;
    private String genero;
    private String ciudadResidencia;
    private List<EstudianteCarreraDTO> carreras;

    public EstudianteDTO(Long LU, String ciudadResidencia, String genero, Long edad, String apellido, String nombre, Long DNI) {
        this.LU = LU;
        this.ciudadResidencia = ciudadResidencia;
        this.genero = genero;
        this.edad = edad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.carreras = new ArrayList<EstudianteCarreraDTO>();
    }

    public Long getLU() {
        return LU;
    }

    public void setLU(Long LU) {
        this.LU = LU;
    }

    public Long getDNI() {
        return DNI;
    }

    public void setDNI(Long DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public void addCarrera(EstudianteCarreraDTO carrera) {
        if(!this.carreras.contains(carrera)) {
            this.carreras.add(carrera);
        }
    }


    @Override
    public String toString() {
        return "EstudianteDTO{" +
                "ciudadResidencia='" + ciudadResidencia + '\'' +
                ", genero='" + genero + '\'' +
                ", LU=" + LU +
                ", DNI=" + DNI +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", carreras=" + carreras +
                '}';
    }
}
