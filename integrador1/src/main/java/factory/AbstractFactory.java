package factory;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.Factura_ProductoDAO;
import dao.ProductoDAO;

public class AbstractFactory {

        public static final int MYSQL_JDBC = 1;
        public static final int DERBY_JDBC = 2;
        public abstract ClienteDAO getClienteDAO();
        public abstract Factura_ProductoDAO getFactura_ProductoDAO();
        public abstract FacturaDAO getFacturaDAO();
        public abstract ProductoDAO getProductoDAO();
        public static AbstractFactory getDAOFactory(int whichFactory) {
            switch (whichFactory) {
                case MYSQL_JDBC : {
                    return MySQLDAOFactory.getInstance();
                }
                case DERBY_JDBC: return null;
                default: return null;
            }
        }
}
