package datos;

import static datos.Conexion.close;
import static datos.Conexion.getConnection;
import domain.Usuario;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDaoJdbc {

    private Connection conexionTransacional;

    private static final String SQL_SELECT = "SELECT k_idUsuario, n_usuario, v_password FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario (n_usuario, v_password) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET n_usuario = ?, v_password = ? WHERE k_idUsuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE k_idUsuario = ?";
    private static final String SQL_CONSULTA = "SELECT k_idUsuario, n_usuario, v_password FROM usuario WHERE n_usuario = ? AND v_password = ?";

    public UsuarioDaoJdbc() {
    }

    public UsuarioDaoJdbc(Connection conexionTransacional) {
        this.conexionTransacional = conexionTransacional;
    }

    public List<Usuario> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = this.conexionTransacional != null ? this.conexionTransacional : Conexion.getConnection();
            stmt = conn.prepareCall(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("k_idUsuario");
                String nombreUsuario = rs.getString("n_usuario");
                String password = rs.getString("v_password");

                usuario = new Usuario(idUsuario, nombreUsuario, password);
                usuarios.add(usuario);
            }

        } finally {
            try {
                rs.close();
                stmt.close();
                if (this.conexionTransacional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

        return usuarios;
    }

    public int insertar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareCall(SQL_INSERT);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPassword());
            registro = stmt.executeUpdate();

        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public int actualizar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareCall(SQL_UPDATE);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdUsuario());
            registro = stmt.executeUpdate();

        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public int eliminar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registro = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareCall(SQL_DELETE);
            stmt.setInt(1, usuario.getIdUsuario());
            registro = stmt.executeUpdate();

        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registro;
    }

    public int loginUser(Usuario usuario) {
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = getConnection();
            stmt = conn.prepareCall(SQL_CONSULTA);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPassword());
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                String user = rs.getString("n_usuario");
                String psw = rs.getString("v_password");
                count ++;
            }
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

}
