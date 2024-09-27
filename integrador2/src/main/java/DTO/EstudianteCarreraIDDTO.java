package main.java.DTO;

public class EstudianteCarreraIDDTO {
    private Long carreiraID;
    private Long estudianteID;


    public EstudianteCarreraIDDTO(Long carreiraID, Long estudianteID) {
        this.carreiraID = carreiraID;
        this.estudianteID = estudianteID;
    }

    //ACA HABRIA QUE CONTROLAR QUE LOS ID QUE LES PASAN TIENEN SENTIDO? O EN
    //EL CONTRUCTOR PONER QUE LE PASAN UN ESTUDIANTE Y UNA CARRERA Y SOLO LE AGARRAMOS LOS id?
    public Long getCarreiraID() {
        return carreiraID;
    }


    public Long getEstudianteID() {
        return estudianteID;
    }

    @Override
    public String toString() {
        return "EstudianteCarreraIDDTO{" +
                "carreiraID=" + carreiraID +
                ", estudianteID=" + estudianteID +
                '}';
    }
}
