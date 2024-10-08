package org.example.integrador3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;



    @Embeddable
    public class EstudianteCarreraID implements Serializable {

        @Column(name= "carrera_ID")
        private Long carreraID;
        @Column(name="estudiante_ID")
        private Long estudianteID;

        public EstudianteCarreraID(Long carreraID, Long estudianteID) {
            this.carreraID = carreraID;
            this.estudianteID = estudianteID;
        }

        public EstudianteCarreraID(){
        }

        public int hashCode(){
            return Objects.hash(carreraID,estudianteID);
        }

    }

