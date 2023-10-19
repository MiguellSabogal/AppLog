package web;

import datos.UsuarioDaoJdbc;
import domain.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "registrar":
                    this.pagRegistrar(request, response);
                    break;
                case "listar":
                    this.usuarioValido(request, response);
                    break;
                case "buscar": 
                    try {
                    this.buscar(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                default:
                    this.usuarioInvalido(request, response);
                    break;
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if (accion != null) {
            switch (accion) {
                case "registrarse": 
                    try {
                    String nombre = request.getParameter("nUsuario");
                    String pswR = request.getParameter("password");
                    Usuario usuario = new Usuario(nombre, pswR);
                    int registrosModificados = new UsuarioDaoJdbc().insertar(usuario);
                    this.usuarioValido(request, response);
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                }
                break;

                default:
                    this.usuarioInvalido(request, response);
                    break;
            }

        }
    }

    private void usuarioInvalido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    private void pagRegistrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspRegistrar = "/paginas/cliente/registrarse.jsp";
        request.getRequestDispatcher(jspRegistrar);
        response.sendRedirect("registrarse.jsp");
    }

    private void pagInicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspInicio = "/paginas/cliente/index.jsp";
        response.sendRedirect(jspInicio);
    }

    private void usuarioValido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuario> usuarios;
            usuarios = new UsuarioDaoJdbc().seleccionar();
            System.out.println("usuarios = " + usuarios);
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarios", usuarios);
            //request.getRequestDispatcher("usuarios.jsp").forward(request, response);
            response.sendRedirect("usuarios.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int count = 0;
        String mensaje = "";
        String nombre = request.getParameter("nUsuario");
        String psw = request.getParameter("password");
        UsuarioDaoJdbc usuarioDao = new UsuarioDaoJdbc();
        Usuario usuario = new Usuario(nombre, psw);
        count = usuarioDao.loginUser(usuario);
        if (count > 0) {
            mensaje = "Login exitoso";

        } else {
            mensaje = "Credenciales incorrectas";
        }
        RequestDispatcher rq = request.getRequestDispatcher("/usuarios.jsp");
        request.setAttribute("mensaje", mensaje + " " + count);
        rq.forward(request, response);
    }

    private void registrarse(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

    }
}
