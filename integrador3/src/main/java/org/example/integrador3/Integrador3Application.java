package org.example.integrador3;

import jakarta.annotation.PostConstruct;
import org.example.integrador3.utils.CargaDeDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "org.example.integrador3")
public class Integrador3Application {

    @Autowired
    private CargaDeDatos cargaDeDatos;


    public static void main(String[] args) {
        SpringApplication.run(Integrador3Application.class, args);
    }

  @PostConstruct
   public void init() throws IOException {
       cargaDeDatos.cargarDesdeCSV();
    }



 }
