package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Vacio {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.getTransaction().commit();
    }

}