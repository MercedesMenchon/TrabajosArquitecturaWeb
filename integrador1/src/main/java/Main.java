import utils.HelperMySQL;
import entities.Cliente;
import entities.Factura;
import entities.Factura_Producto;
import entities.Producto;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws Exception {

        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();


        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        System.out.println();
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println();
        DireccionDAO direccion = chosenFactory.getDireccionDAO();
        PersonaDAO persona = chosenFactory.getPersonaDAO();


        System.out.println("Busco una Persona por id: ");
        Persona personaById = person


    }
}
