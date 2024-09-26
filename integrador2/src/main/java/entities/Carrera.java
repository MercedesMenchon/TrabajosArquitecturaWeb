package main.java.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Carrera implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCarrera;
    @Column(nullable = false)
    private String nombreCarrera;
    @OneToMany (mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudiantes;


    // AL GENERARSE EL ID AUTOMATICO, HABRIA QUE PASARLE UN ID CUANDO LO INSTANCIAMOS?
    public Carrera(/*Long idCarrera,*/ String nombreCarrera) {
       // this.idCarrera = idCarrera;
        super();
        this.nombreCarrera = nombreCarrera;
        this.estudiantes = new ArrayList<EstudianteCarrera>();
    }

    public Carrera() {
        super();
        this.estudiantes = new ArrayList<EstudianteCarrera>();
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
}
