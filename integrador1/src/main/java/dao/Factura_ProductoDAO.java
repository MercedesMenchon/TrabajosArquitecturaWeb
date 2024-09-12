package dao;

import entities.Factura_Producto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Factura_ProductoDAO {
    private Connection conn;

    public Factura_ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Factura_Producto factura_producto) throws Exception {
        String query = "INSERT INTO Factura_Producto (idProducto, idFactura, cantidad) VALUES (?, ?,?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura_producto.getIdProducto());
            ps.setInt(2, factura_producto.getIdFactura());
            ps.setInt(2, factura_producto.getCantidad());


            ps.executeUpdate();
            System.out.println("factura_producto insertado exitosamente.");
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


    public void delete(Factura_Producto factura_producto) throws Exception {
        String query = "DELETE FROM Factura_Producto WHERE idProducto = ? AND idFactura = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura_producto.getIdProducto());
            ps.setInt(2, factura_producto.getIdFactura());

            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Factura_Producto eliminado exitosamente.");
            } else {
                System.out.println("No se encontró ningún registro con la factura_producto");
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
    }

    public boolean updateFacturaProducto(Factura_Producto facturaProducto) throws Exception {
        String checkFacturaQuery = "SELECT COUNT(*) FROM factura WHERE idFactura = ?";
        String checkProductoQuery = "SELECT COUNT(*) FROM producto WHERE idProducto = ?";
        String updateQuery = "UPDATE factura_producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
        PreparedStatement checkFacturaStmt = null;
        PreparedStatement checkProductoStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            // Verificar si idFactura existe
            checkFacturaStmt = conn.prepareStatement(checkFacturaQuery);
            checkFacturaStmt.setInt(1, facturaProducto.getIdFactura());
            rs = checkFacturaStmt.executeQuery();
            rs.next();
            int facturaCount = rs.getInt(1);

            if (facturaCount == 0) {
                System.out.println("No existe el idFactura dado para actualizar la factura_producto.");
                return false;
            }

            // Verificar si idProducto existe
            checkProductoStmt = conn.prepareStatement(checkProductoQuery);
            checkProductoStmt.setInt(1, facturaProducto.getIdProducto());
            rs = checkProductoStmt.executeQuery();
            rs.next();
            int productoCount = rs.getInt(1);

            if (productoCount == 0) {
                System.out.println("No existe el idProducto dado para actualizar la factura_producto.");
                return false;
            }

            // Continuar con la actualización si ambos idFactura e idProducto son válidos
            updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, facturaProducto.getCantidad());
            updateStmt.setInt(2, facturaProducto.getIdFactura());
            updateStmt.setInt(3, facturaProducto.getIdProducto());

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Factura_Producto actualizado exitosamente.");
                return true;
            } else {
                System.out.println("No se encontró la entrada de Factura_Producto a actualizar.");
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
                if (checkFacturaStmt != null) {
                    checkFacturaStmt.close();
                }
                if (checkProductoStmt != null) {
                    checkProductoStmt.close();
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

    public Factura_Producto find(int idProducto, int idFactura) {
        String query = "SELECT * FROM Factura_Producto WHERE idProducto = ? AND idFactura = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Factura_Producto facturaProducto = null;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProducto);
            pstmt.setInt(2, idFactura);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                facturaProducto = new Factura_Producto(idProducto, idFactura, cantidad);
            } else {
                System.out.println("No se encontró ningún registro con idProducto: " + idProducto + " y idFactura: " + idFactura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return facturaProducto;
    }



    public List<Factura_Producto> selectList() {
        String query = "SELECT * " +
                "FROM factura_producto ";

        Factura_Producto factura_productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Factura_Producto> listado = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            listado = new ArrayList<Factura_Producto>();
            while (rs.next()) {

                int idProducto = rs.getInt("idProducto");
                int idFactura = rs.getInt("idFactura");
                int cantidad = rs.getInt("cantidad");

                Factura_Producto factura_producto = new  Factura_Producto(idProducto, idFactura, cantidad);
                listado.add(factura_producto);
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


