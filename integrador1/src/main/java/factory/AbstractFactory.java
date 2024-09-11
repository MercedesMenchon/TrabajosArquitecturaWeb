package factory;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_ProductoDAO;
import dao.ProductoDAO;

public abstract class AbstractFactory {

        public static final int MYSQL_JDBC = 1;
        public abstract ClienteDAO getClienteDAO();
        public abstract Factura_ProductoDAO getFactura_ProductoDAO();
        public abstract FacturaDAO getFacturaDAO();
        public abstract ProductoDAO getProductoDAO();
        public static AbstractFactory getDAOFactory(int whichFactory) {
                    return MySQLDAOFactory.getInstance();

        }
}
