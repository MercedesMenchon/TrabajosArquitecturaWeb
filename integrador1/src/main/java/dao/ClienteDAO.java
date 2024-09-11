package dao;

import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        /*
            public boolean delete(Integer id) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'delete'");
            }

        */
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
                    clienteById = new Cliente (pk, nombre, email);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
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

/*
        public ClienteDTO findClienteDTO(Integer pk) {
            String query = "SELECT c.idCliente, c.nombre, c.email" +
                    "FROM Cliente c " +
                    "JOIN Factura f ON c.idCliente = f.idCliente " +
                    "WHERE c.idCliente = ?";
            Cliente clienteById = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            ClienteDTO clienteDTO = null;
            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
                rs = ps.executeQuery();
                if (rs.next()) { // Verificar si hay resultados
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");
                    int idCliente = rs.getInt("idCliente");
                    int idFactura = rs.getInt("idFactura");

                    // Crear una nueva instancia de ClienteDTO con los datos recuperados de la consulta
                    clienteDTO = new ClienteDTO(idCliente, nombre, email, idFactura);
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

            return clienteDTO;
        }



    public boolean update(Persona dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    public List<Persona> selectList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectList'");
    }

 */



}
