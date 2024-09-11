import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_ProductoDAO;
import dao.ProductoDAO;
import factory.AbstractFactory;
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
        ProductoDAO productoDAO = chosenFactory.getProductoDAO();
        ClienteDAO clienteDAO = chosenFactory.getClienteDAO();
        FacturaDAO facturaDAO = chosenFactory.getFacturaDAO();
        Factura_ProductoDAO factura_productoDAO = chosenFactory.getFactura_ProductoDAO();

       /* System.out.println("Busco un Persona por id: ");
        Persona personaById = person
*/

    }
}
