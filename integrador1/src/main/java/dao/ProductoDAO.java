package dao;

import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conn;

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Producto Producto) throws Exception {
        String query = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?,?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, Producto.getIdProducto());
            ps.setString(2, Producto.getNombre());
            ps.setFloat(2, Producto.getValor());


            ps.executeUpdate();
            System.out.println("Producto insertado exitosamente.");
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



    public boolean delete(Integer id) throws Exception {
        String query = "DELETE FROM producto WHERE idProducto = ?";
        PreparedStatement ps = null;
        boolean deleted = false;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true;
                System.out.println("Producto eliminado exitosamente.");
            }
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

        return deleted;
    }


    public Producto find(int id) throws Exception {
        String query = "SELECT idProducto, nombre, valor FROM Producto WHERE idProducto = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producto producto = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);  // Similar al uso de parámetros en la sentencia UPDATE

            rs = ps.executeQuery();
            if (rs.next()) {
                int idP = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                Float valor = rs.getFloat("valor");
                producto = new Producto(idP,nombre,valor);
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return producto;
    }


    public boolean update(Producto producto) throws Exception {
        String query = "UPDATE Producto SET nombre = ?, valor = ? WHERE idProducto = ?";
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, producto.getNombre());
            ps.setFloat(2, producto.getValor());
            ps.setInt(3, producto.getIdProducto());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
                System.out.println("Producto actualizado exitosamente.");
            }
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

        return updated;
    }




    public List<Producto> selectList() {
        String query = "SELECT * " +
                "FROM producto ";

        Producto productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Producto> listado = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            // Crear una nueva instancia de Producto con los datos recuperados de la consulta
            listado = new ArrayList<Producto>();
            while (rs.next()) { // Verificar si hay resultados

                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");

                Producto Producto = new Producto(idProducto, nombre, valor);
                listado.add(Producto);
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

    public Producto productoMasRecaudo() {
        String query = "SELECT p.idProducto, p.nombre, p.valor, SUM(fp.cantidad * p.valor) AS total_recaudado " +
                "FROM Producto p " +
                "JOIN Factura_Producto fp ON p.idProducto = fp.idProducto " +
                "GROUP BY p.idProducto " +
                "ORDER BY total_recaudado DESC " +
                "LIMIT 1;";

        Producto producto = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");

                producto = new Producto(idProducto, nombre, valor);

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

        return producto;
    }
    }






