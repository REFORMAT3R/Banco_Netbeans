/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseDatos;

/**
 *
 * @author AVATEC
 */

import java.sql.*;
import modelo.*;
import java.util.*;

public class UsuarioDAO {
    /* Método para INICIAR SESIÓN. */
    public static Usuario login(String nombreUsuario, String contrasenia) {
        String sql = "SELECT * FROM usuario WHERE nombreUsuario = ? AND contrasenia = ? AND estado = 1";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Obtener datos básicos
                    String tipo = rs.getString("tipo"); // 'CLIENTE', 'EMPLEADO', 'ADMIN'
                    String codigoCliente = rs.getString("codigoCliente");
                    String codigoEmpleado = rs.getString("codigoEmpleado");
                    boolean estado = rs.getInt("estado") == 1;

                    // 2. Dependiendo del tipo, instanciar la clase correcta
                    if (tipo.equalsIgnoreCase("CLIENTE")) {
                        // Buscamos los datos personales del cliente usando ClienteDAO
                        Cliente cliente = ClienteDAO.obtenerCliente(codigoCliente);
                        return new UsuarioCliente(nombreUsuario, contrasenia, estado, cliente);

                    } else if (tipo.equalsIgnoreCase("EMPLEADO")) {
                        Empleado empleado = EmpleadoDAO.obtenerEmpleado(codigoEmpleado);
                        return new UsuarioEmpleado(nombreUsuario, contrasenia, estado, empleado);

                    } else if (tipo.equalsIgnoreCase("ADMIN")) {
                        Empleado e = EmpleadoDAO.obtenerEmpleado(codigoEmpleado);

                        if (e != null) {
                            Administrador admin = new Administrador(
                                e.getNombre(), 
                                e.getApellido(), 
                                e.getTelefono(), 
                                e.getCorreo(), 
                                e.getEdad(), 
                                e.getDni(), 
                                e.getDireccion(), 
                                e.getCodigoEmpleado()
                            );

                            // 3. Devolvemos el usuario administrador correcto
                            return new UsuarioAdministrador(nombreUsuario, contrasenia, estado, admin);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null; // Retorna null si no encuentra usuario o falla
    }

    /*Método para REGISTRAR un nuevo usuario.*/
    
    public static boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombreUsuario, contrasenia, estado, tipo, codigoCliente, codigoEmpleado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Datos Comunes
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setInt(3, usuario.getEstado() ? 1 : 0);

            // Lógica para determinar Tipo y Códigos
            if (usuario instanceof UsuarioCliente) {
                UsuarioCliente uc = (UsuarioCliente) usuario;
                ps.setString(4, "CLIENTE");
                ps.setString(5, uc.getCliente().getCodigoCliente()); // codigoCliente
                ps.setNull(6, java.sql.Types.VARCHAR);               // codigoEmpleado es NULL

            } else if (usuario instanceof UsuarioAdministrador) {
                UsuarioAdministrador ua = (UsuarioAdministrador) usuario;
                ps.setString(4, "ADMIN");
                ps.setNull(5, java.sql.Types.VARCHAR);               // codigoCliente es NULL
                ps.setString(6, ua.getAdministrador().getCodigoEmpleado()); // codigoEmpleado

            } else if (usuario instanceof UsuarioEmpleado) {
                UsuarioEmpleado ue = (UsuarioEmpleado) usuario;
                ps.setString(4, "EMPLEADO");
                ps.setNull(5, java.sql.Types.VARCHAR);               // codigoCliente es NULL
                ps.setString(6, ue.getEmpleado().getCodigoEmpleado()); // codigoEmpleado
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Verificar si ya existe el nombre de usuario
    public static boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT idUsuario FROM usuario WHERE nombreUsuario = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar usuario
    public static boolean eliminarUsuarioPorCodigo(String codigo) {
        // Buscamos coincidencia en cualquiera de las dos columnas de referencia
        String sql = "DELETE FROM usuario WHERE codigoCliente = ? OR codigoEmpleado = ?";

        try (Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            // Pasamos el mismo código a ambos parámetros del SQL (?)
            ps.setString(1, codigo);
            ps.setString(2, codigo);

            // Si borra al menos una fila, devuelve true
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario por código: " + e.getMessage());
            return false;
        }
    }
    //Leer todo
    public static List<Usuario> listarUsuarios() {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombreUsuario");
                String contrasenia = rs.getString("contrasenia");
                boolean estado = rs.getInt("estado") == 1;
                String tipo = rs.getString("tipo");
                String codigoCliente = rs.getString("codigoCliente");
                String codigoEmpleado = rs.getString("codigoEmpleado");

                Usuario u = null;

                // Reconstruimos el objeto según su tipo
                if ("CLIENTE".equalsIgnoreCase(tipo)) {
                    Cliente c = ClienteDAO.obtenerCliente(codigoCliente);
                    u = new UsuarioCliente(nombreUsuario, contrasenia, estado, c);
                } else if ("EMPLEADO".equalsIgnoreCase(tipo)) {
                    Empleado e = EmpleadoDAO.obtenerEmpleado(codigoEmpleado);
                    u = new UsuarioEmpleado(nombreUsuario, contrasenia, estado, e);
                } else if ("ADMIN".equalsIgnoreCase(tipo)) {
                     Empleado e = EmpleadoDAO.obtenerEmpleado(codigoEmpleado);
                     if(e != null) {
                         Administrador admin = new Administrador(e.getNombre(), e.getApellido(), e.getTelefono(), e.getCorreo(), e.getEdad(), e.getDni(), e.getDireccion(), e.getCodigoEmpleado());
                         u = new UsuarioAdministrador(nombreUsuario, contrasenia, estado, admin);
                     }
                }

                if (u != null) {
                    lista.add(u);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
    
    // Validar si un empleado es Administrador
    public static boolean esAdministrador(String codigoEmpleado) {
        String sql = "SELECT tipo FROM usuario WHERE codigoEmpleado = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, codigoEmpleado);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    return "ADMIN".equalsIgnoreCase(tipo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar rol de admin: " + e.getMessage());
        }
        return false;
    }
}
