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


    public boolean deleteProducto(Integer idProducto) {
        String query = "DELETE FROM producto WHERE idProducto = ?";
        PreparedStatement pstmt = null;
        boolean eliminado = false;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProducto);

            int filasEliminadas = pstmt.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Producto con id: " + idProducto + " eliminado con éxito.");
                eliminado = true;
            } else {
                System.out.println("No se encontró ningún producto con id: " + idProducto + ".");
            }
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) { // SQLState 23XXX codes indicate integrity constraint violation
                System.out.println("No se puede eliminar un producto con una factura_producto asociada.");
            } else {
                e.printStackTrace(); // Opcional: manejar otros errores SQL
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                conn.commit(); // Asegúrate de que el commit sea necesario en tu caso
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return eliminado;
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

    public boolean updateProducto(Producto producto) throws Exception {
        String checkProductQuery = "SELECT COUNT(*) FROM producto WHERE idProducto = ?";
        String updateQuery = "UPDATE producto SET nombre = ?, valor = ? WHERE idProducto = ?";
        PreparedStatement checkProductStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            // Verificar si idProducto existe
            checkProductStmt = conn.prepareStatement(checkProductQuery);
            checkProductStmt.setInt(1, producto.getIdProducto());
            rs = checkProductStmt.executeQuery();
            rs.next();
            int productCount = rs.getInt(1);

            if (productCount == 0) {
                System.out.println("No existe el idProducto dado para actualizar el producto.");
                return false;
            }

            // Continuar con la actualización si el idProducto es válido
            updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, producto.getNombre());
            updateStmt.setDouble(2, producto.getValor());
            updateStmt.setInt(3, producto.getIdProducto());

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Producto actualizado exitosamente.");
                return true;
            } else {
                System.out.println("No se encontró el producto a actualizar.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (checkProductStmt != null) {
                    checkProductStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                conn.commit(); // Asegúrate de que el commit sea necesario en tu caso
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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






