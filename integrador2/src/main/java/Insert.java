package main.java;

import main.java.entities.Carrera;
import main.java.entities.Estudiante;
import main.java.entities.EstudianteCarrera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Insert {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

//        Carrera c1 = new Carrera("TUDAI");
//        em.persist(c1);
//
//        Estudiante e1 = new Estudiante("Juan", "Garcia", 28, "Masculino", 35496951, "Tandil");
//        Estudiante e2 = new Estudiante("Ana", "Leiva", 22, "Femenino", 40568953, "Tandil");
//        em.persist(e1);
//        em.persist(e2);
//        System.out.println("c1.getIdCarrera()"+ c1.getIdCarrera());
//        System.out.println("e1.getLU()"+ e1.getLU());

//      PRIMERO RUNNEAR ASI Y DESPUES DESCOMENTAR LO DE ABAJO Y COMENTAR LO DE ARRIBA
//      (SI NO ANDA REVISAR ID)

        Carrera c = em.find(Carrera.class,1L);
        Estudiante e = em.find(Estudiante.class,2L);
        EstudianteCarrera ec = new EstudianteCarrera(e,c);
        em.persist(ec);
        System.out.println();

        em.getTransaction().commit();

    }

}