package dao;
import entities.Cliente;
import entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class FacturaDAO {
    private Connection conn;

    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Factura factura) throws Exception {
        String query = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());

            ps.executeUpdate();
            System.out.println("Factura insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public int delete(int idFactura) throws Exception {
        String query = "DELETE FROM factura WHERE idFactura = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, idFactura);  // Asigna el valor de idFactura al parámetro de la consulta

            int rowsAffected = ps.executeUpdate();  // Ejecuta la consulta
            System.out.println("Factura eliminada exitosamente.");
            return rowsAffected;  // Devuelve el número de filas afectadas (debería ser 1 si se eliminó con éxito)
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;  // Si ocurre una excepción, devuelve 0 (indica que no se eliminó ninguna fila)
        } finally {
            try {
                if (ps != null) {
                    ps.close();  // Cierra el PreparedStatement
                }
                conn.commit();  // Asegura que la transacción se confirme
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


        public Factura find(int idFactura) throws Exception {
            String query = "SELECT idFactura, idCliente FROM factura WHERE idFactura = ?";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Factura factura = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, idFactura);  // Establece el idFactura como parámetro

                rs = ps.executeQuery();  // Ejecuta la consulta

                // Si se encuentra un registro, crea un objeto Factura con los datos obtenidos
                if (rs.next()) {

                  int idF =   rs.getInt("idFactura");
                    int idC = rs.getInt("idCliente");
                 factura = new Factura(idF,idC);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();  // Cierra el ResultSet
                    }
                    if (ps != null) {
                        ps.close();  // Cierra el PreparedStatement
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return factura;  // Devuelve una copia de la factura encontrada (o null si no se encontró)

    }


        public boolean update(Factura factura) throws Exception {
            String query = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(query);


                ps.setInt(1, factura.getIdCliente());
                ps.setInt(2, factura.getIdFactura());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Factura actualizada exitosamente.");
                    return true;
                } else {
                    System.out.println("No se encontró la factura a actualizar.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }




    public List<Factura> selectList() {
        String query = "SELECT * " +
                "FROM Factura ";

        Factura FacturaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Factura> listado = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            // Crear una nueva instancia de Factura con los datos recuperados de la consulta
            listado = new ArrayList<Factura>();
            while (rs.next()) { // Verificar si hay resultados
                int idFactura = rs.getInt("idFactura");
                int idCliente = rs.getInt("idCliente");
                Factura factura = new Factura(idFactura, idCliente);
                listado.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listado;
    }
}
