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

        public boolean deleteFactura(Integer idFactura) {
            String query = "DELETE FROM factura WHERE idFactura = ?";
            PreparedStatement pstmt = null;
            boolean eliminado = false;

            try {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, idFactura);

                int filasEliminadas = pstmt.executeUpdate();

                if (filasEliminadas > 0) {
                    System.out.println("Factura con id: " + idFactura + " eliminada con éxito.");
                    eliminado = true;
                } else {
                    System.out.println("No se encontró ninguna factura con id: " + idFactura + ".");
                }
            } catch (SQLException e) {
                if (e.getSQLState().startsWith("23")) { // SQLState 23XXX codes indicate integrity constraint violation
                    System.out.println("No se puede eliminar una factura que tiene una factura_producto asociada.");
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


        public Factura find(int idFactura) throws Exception {
            String query = "SELECT idFactura, idCliente FROM factura WHERE idFactura = ?";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Factura factura = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, idFactura);

                rs = ps.executeQuery();

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
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return factura;

        }

        public boolean updateFactura(Factura factura) throws Exception {
            String checkClientQuery = "SELECT COUNT(*) FROM cliente WHERE idCliente = ?";
            String updateQuery = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
            PreparedStatement checkClientStmt = null;
            PreparedStatement updateStmt = null;
            ResultSet rs = null;

            try {
                // Verificar si idCliente existe en la tabla cliente
                checkClientStmt = conn.prepareStatement(checkClientQuery);
                checkClientStmt.setInt(1, factura.getIdCliente());
                rs = checkClientStmt.executeQuery();
                rs.next();
                int clientCount = rs.getInt(1);

                if (clientCount == 0) {
                    System.out.println("No existe el idCliente dado para actualizar la factura.");
                    return false;
                }

                // Continuar con la actualización si el idCliente es válido
                updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, factura.getIdCliente());
                updateStmt.setInt(2, factura.getIdFactura());

                int rowsAffected = updateStmt.executeUpdate();

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
                    if (rs != null) {
                        rs.close();
                    }
                    if (checkClientStmt != null) {
                        checkClientStmt.close();
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