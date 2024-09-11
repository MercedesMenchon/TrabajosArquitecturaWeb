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



    public void update(Factura_Producto factura_producto) throws Exception {
        String query = "UPDATE Factura_Producto SET cantidad = ? WHERE idProducto = ? AND idFactura = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura_producto.getCantidad());
            ps.setInt(2, factura_producto.getIdProducto());
            ps.setInt(3, factura_producto.getIdFactura());

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Factura_Producto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró ningún registro con los ID especificados.");
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


