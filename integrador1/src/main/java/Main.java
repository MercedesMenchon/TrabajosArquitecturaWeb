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
        System.out.println("---------------------------------------------");
        System.out.println();
        ProductoDAO productoDAO = chosenFactory.getProductoDAO();
        ClienteDAO clienteDAO = chosenFactory.getClienteDAO();
        FacturaDAO facturaDAO = chosenFactory.getFacturaDAO();
        Factura_ProductoDAO factura_productoDAO = chosenFactory.getFactura_ProductoDAO();


        System.out.println("ACTIVIDAD 3");

/*
3) Escriba un programa JDBC que retorne el producto que más recaudó. Se define
“recaudación” como cantidad de productos vendidos multiplicado por su valor.
.
 */
        System.out.println("Buscamos el producto que más recaudó ");
        System.out.println( productoDAO.productoMasRecaudo());


        System.out.println("ACTIVIDAD 4");
   /*
   4) Escriba un programa JDBC que imprima una lista de clientes, ordenada por a cuál se le
facturó más.
    */
        System.out.println("Obtenemos la lista de los clientes ordenada según el que más facturó:");
        System.out.println( clienteDAO.findClientesByFacturado());




        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");

        System.out.println("Ejecutamos los demás métodos para verificar el funcionamiento:");

        Factura_Producto fp = new Factura_Producto(1,52, 1000);
        Factura f = new Factura(400, 900);
        Cliente c = new Cliente(1,"Juan", "Juan@gmail.com");
        Producto p = new Producto(1, "Arroz", 0.1f);



        System.out.println("Lists------------");
        System.out.println(clienteDAO.selectList());
        System.out.println(productoDAO.selectList());
        System.out.println(facturaDAO.selectList());
        System.out.println(factura_productoDAO.selectList());
        System.out.println();
        System.out.println("Finds -----------");
        System.out.println(clienteDAO.find(1));
        System.out.println(productoDAO.find(1));
        System.out.println(facturaDAO.find(1));
        System.out.println(factura_productoDAO.find(41,2));
        System.out.println();
        System.out.println("Updates----------");
        clienteDAO.updateCliente(c);
        productoDAO.updateProducto(p);
        facturaDAO.updateFactura(f);
        factura_productoDAO.updateFacturaProducto(fp);
        System.out.println();
        System.out.println("Deletes-----------");
        productoDAO.deleteProducto(80);
        clienteDAO.deleteCliente(3);
        factura_productoDAO.delete(fp);
    }
}
