package utils;
import entities.Cliente;
import entities.Factura;
import entities.Factura_Producto;
import entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HelperMySQL {

    private Connection conn = null;

    public HelperMySQL() {//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/integrador1";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {


            String dropFactura_Producto = "DROP TABLE IF EXISTS factura_producto";
            this.conn.prepareStatement(dropFactura_Producto).execute();
            this.conn.commit();

        String dropFactura = "DROP TABLE IF EXISTS factura";
        this.conn.prepareStatement(dropFactura).execute();
        this.conn.commit();

        String dropProducto = "DROP TABLE IF EXISTS producto";
        this.conn.prepareStatement(dropProducto).execute();
        this.conn.commit();

        String dropCliente = "DROP TABLE IF EXISTS cliente";
        this.conn.prepareStatement(dropCliente).execute();
        this.conn.commit();


    }

    public void createTables() throws SQLException {
        String tableCliente = "CREATE TABLE IF NOT EXISTS Cliente(" +
                "idCliente INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "CONSTRAINT Cliente_pk PRIMARY KEY (idCliente));";
        this.conn.prepareStatement(tableCliente).execute();
        this.conn.commit();

        String tableFactura = "CREATE TABLE IF NOT EXISTS Factura(" +
                "idFactura INT NOT NULL, " +
                "idCliente INT NOT NULL, " +

                "CONSTRAINT Factura_pk PRIMARY KEY (idFactura), " +
                "CONSTRAINT FK_idCliente FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente))";
        this.conn.prepareStatement(tableFactura).execute();
        this.conn.commit();

        String tableProducto = "CREATE TABLE IF NOT EXISTS Producto (" +
                "    idProducto INT NOT NULL," +
                "    nombre VARCHAR(45)," +
                "    valor FLOAT," +
                "    CONSTRAINT Producto_pk PRIMARY KEY (idProducto)" +
                ");";
        this.conn.prepareStatement(tableProducto).execute();
        this.conn.commit();

        String tableFactura_Producto = "CREATE TABLE IF NOT EXISTS Factura_Producto(" +
                " idFactura INT NOT NULL," +
                " idProducto INT NOT NULL, " +
                " cantidad INT, " +
                " CONSTRAINT FK_Factura FOREIGN KEY (idFactura) REFERENCES Factura(idFactura), " +
                " CONSTRAINT FK_Producto FOREIGN KEY (idProducto) REFERENCES Producto(idProducto), " +
                " PRIMARY KEY (idFactura, idProducto)" +
                ");";


        this.conn.prepareStatement(tableFactura_Producto).execute();
        this.conn.commit();

    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
       String path = "integrador1\\src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};  // Puedes configurar tu encabezado personalizado aquí si es necesario
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader(header).parse(in);
        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

    public void populateDB() throws Exception {
        try {
          System.out.println("Populating DB...");
            for (CSVRecord row : getData("clientes.csv")) {
                if (row.size() >= 3) { // Verificar que hay al menos 3 campos en el CSVRecord
                    Integer id = Integer.parseInt(row.get(0)); //ver si es necesario parsear a int
                    String nombre = row.get(1);
                    String email = row.get(2);
                    if (!nombre.isEmpty() && !email.isEmpty()) {
                        try {

                            Cliente cliente = new Cliente(id, nombre, email);
                            insertCliente(cliente, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de cliente: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Clientes insertados");

            for (CSVRecord row : getData("productos.csv")) {
                if (row.size() >= 3) { // Verificar que hay al menos 3 campos en el CSVRecord
                    Integer id = Integer.parseInt(row.get(0));
                    String nombre = row.get(1);
                    Float valor = Float.valueOf(row.get(2));

                    if (!nombre.isEmpty() && valor >= 0) {
                        try {
                            Producto producto = new Producto(id, nombre, valor);
                            insertProducto(producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de producto: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Productos insertados");

            for (CSVRecord row : getData("facturas.csv")) {
                if (row.size() >= 2) { // Verificar que hay al menos 2 campos en el CSVRecord
                    Integer idFactura = Integer.parseInt(row.get(0));
                    Integer idCliente = Integer.parseInt(row.get(1));

                        try {
                            Factura factura = new Factura(idFactura, idCliente);
                            insertFactura(factura, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de factura: " + e.getMessage());
                        }

                }
            }

            System.out.println("Facturas insertadas");

            for (CSVRecord row : getData("facturas-productos.csv")) {
                if (row.size() >= 3) {
                    Integer idFactura = Integer.parseInt(row.get(0));
                    Integer idProducto = Integer.parseInt(row.get(1));
                    Integer cantidad = Integer.parseInt(row.get(2));
                    if (cantidad>0) {
                        try {
                            Factura_Producto factura_producto = new Factura_Producto(idFactura, idProducto, cantidad);
                            insertFactura_Producto(factura_producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de factura_producto: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("Facturas_Producto insertadas");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int insertCliente(Cliente cliente, Connection conn) throws Exception {
        String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }


    private int insertProducto(Producto producto, Connection conn) throws Exception {

        String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFactura(Factura factura, Connection conn) throws Exception {

        String insert = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());


            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }


    private int insertFactura_Producto(Factura_Producto factura_producto, Connection conn) throws Exception {

        String insert = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, factura_producto.getIdFactura());
            ps.setInt(2, factura_producto.getIdProducto());
            ps.setInt(3, factura_producto.getCantidad());


            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }



    private void closePsAndCommit(Connection conn, PreparedStatement ps) {
        if (conn != null){
            try {
                ps.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

