package dao;

import entities.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ClienteDAO {

        private Connection conn;

        public ClienteDAO(Connection conn) {
            this.conn = conn;
        }


        public void insertCliente(Cliente cliente) {
            String query = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, cliente.getId()); // idPersona
                ps.setString(2, cliente.getNombre()); // nombre
                ps.setString(3, cliente.getEmail()); // edad

                ps.executeUpdate();
                System.out.println("Cliente insertada exitosamente.");
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


        public Cliente find(Integer pk) {
            String query = "SELECT c.nombre, c.email" +
                    "FROM Cliente c " +
                    "WHERE c.idCliente = ?";
            Cliente clienteById = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
                rs = ps.executeQuery();
                if (rs.next()) { // Verificar si hay resultados
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");


                    // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                    clienteById = new Cliente(pk, nombre, email);
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
            return clienteById;
        }

        public boolean deleteCliente(Integer idCliente) {
            String query = "DELETE FROM cliente WHERE idCliente = ?";
            PreparedStatement pstmt = null;
            boolean eliminado = false;

            try {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, idCliente);

                int filasEliminadas = pstmt.executeUpdate();

                if (filasEliminadas > 0) {
                    System.out.println("Cliente con id: " + idCliente + " eliminado con éxito.");
                    eliminado = true;
                } else {
                    System.out.println("No se encontró ningún cliente con id: " + idCliente + ".");
                }
            } catch (SQLException e) {
                // Verificar si la excepción está relacionada con la restricción de clave foránea
                if (e.getErrorCode() == 1451) {  // 1451 es el código de error MySQL para "Cannot delete or update a parent row"
                    System.out.println("No se puede eliminar un cliente con una factura asociada.");
                } else {
                    e.printStackTrace(); // Manejo de otros errores SQL
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


        public boolean updateCliente(Cliente cliente) throws Exception {
            String checkClientQuery = "SELECT COUNT(*) FROM cliente WHERE idCliente = ?";
            String updateQuery = "UPDATE cliente SET nombre = ?, email = ? WHERE idCliente = ?";
            PreparedStatement checkClientStmt = null;
            PreparedStatement updateStmt = null;
            ResultSet rs = null;

            try {
                // Verificar si idCliente existe
                checkClientStmt = conn.prepareStatement(checkClientQuery);
                checkClientStmt.setInt(1, cliente.getId());
                rs = checkClientStmt.executeQuery();
                rs.next();
                int clientCount = rs.getInt(1);

                if (clientCount == 0) {
                    System.out.println("No existe el idCliente dado para actualizar el cliente.");
                    return false;
                }

                // Continuar con la actualización si el idCliente es válido
                updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, cliente.getNombre());
                updateStmt.setString(2, cliente.getEmail());
                updateStmt.setInt(3, cliente.getId());

                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Cliente actualizado exitosamente.");
                    return true;
                } else {
                    System.out.println("No se encontró el cliente a actualizar.");
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

        public List<Cliente> selectList() {
            List<Cliente> clientes = new ArrayList<>();
            String query = "SELECT * FROM cliente";
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int idCliente = rs.getInt("idCliente");
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");

                    Cliente cliente = new Cliente(idCliente, nombre, email);
                    clientes.add(cliente);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return clientes;
        }


        public Cliente find(int id) {
            String query = "SELECT nombre, email FROM Cliente WHERE idCliente = ?";
            Cliente clienteById = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, id); // Establecer el parámetro en la consulta SQL
                rs = ps.executeQuery();
                if (rs.next()) { // Verificar si hay resultados
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");


                    // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                    clienteById = new Cliente(id, nombre, email);
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
            return clienteById;
        }


        public List<Cliente> findClientesByFacturado() {
            String query = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS totalFacturado " +
                    "FROM Cliente c " +
                    "JOIN Factura f ON c.idCliente = f.idCliente " +
                    "JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
                    "JOIN Producto p ON fp.idProducto = p.idProducto " +
                    "GROUP BY c.idCliente, c.nombre, c.email " +
                    "ORDER BY totalFacturado DESC";

            List<Cliente> clientesOrdenados = new ArrayList<>();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()) {
                    int idCliente = rs.getInt("idCliente");
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");
                    double totalFacturado = rs.getDouble("totalFacturado");

                    // Crear el objeto Cliente y establecer el total facturado
                    Cliente cliente = new Cliente(idCliente, nombre, email);
                    clientesOrdenados.add(cliente);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.commit();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return clientesOrdenados;
        }

    }