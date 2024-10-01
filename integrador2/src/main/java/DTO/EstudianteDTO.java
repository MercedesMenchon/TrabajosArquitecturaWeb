package main.java.DTO;

import main.java.entities.Estudiante;


public class EstudianteDTO {
    private Long LU;
    private Long DNI;
    private String nombre;
    private String apellido;
    private Long edad;
    private String genero;
    private String ciudadResidencia;


    public EstudianteDTO(Long LU, String ciudadResidencia, String genero, Long edad, String apellido, String nombre, Long DNI) {
        this.LU = LU;
        this.ciudadResidencia = ciudadResidencia;
        this.genero = genero;
        this.edad = edad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
    }
    public EstudianteDTO(Estudiante est) {

            this.LU=est.getLU();
                this.ciudadResidencia=est.getCiudadResidencia();
                this.genero=est.getGenero();
                this.edad=est.getEdad();
                this.apellido=est.getApellido();
                this.nombre=est.getNombre();
                this.DNI=est.getDNI();



    }
    public Long getLU() {
        return LU;
    }



    public Long getDNI() {
        return DNI;
    }



    public String getNombre() {
        return nombre;
    }



    public String getApellido() {
        return apellido;
    }



    public Long getEdad() {
        return edad;
    }



    public String getGenero() {
        return genero;
    }



    public String getCiudadResidencia() {
        return ciudadResidencia;
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
                '}';
    }
}
