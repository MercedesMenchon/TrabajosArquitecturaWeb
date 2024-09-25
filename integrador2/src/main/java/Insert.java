package main.java;

import main.java.dao.Carrera;
import main.java.dao.Estudiante;
import main.java.dao.EstudianteCarreraID;
import main.java.dao.EstudianteCarrera;

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
//        EstudianteCarrera ecID1 = new EstudianteCarrera();
//        em.persist(ecID1);
        Carrera c = em.find(Carrera.class,13L);
        Estudiante e = em.find(Estudiante.class,14L);
        EstudianteCarrera ec = new EstudianteCarrera(e,c);
        em.persist(ec);
        System.out.println();

//        EstudianteCarrera es1 = new EstudianteCarrera(true, 10, "TUDAI", c1, e1, ecID1);
//        em.persist(es1);

        em.getTransaction().commit();

    }

}