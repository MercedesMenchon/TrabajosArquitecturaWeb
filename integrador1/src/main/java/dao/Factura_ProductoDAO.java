package dao;

import entities.Factura_Producto;
import entities.Producto;

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

    /*

    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    public Producto find(Integer pk) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }


    public boolean update(Producto dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


     */

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
            // Crear una nueva instancia de Factura_Producto con los datos recuperados de la consulta
            listado = new ArrayList<Factura_Producto>();
            while (rs.next()) { // Verificar si hay resultados

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
