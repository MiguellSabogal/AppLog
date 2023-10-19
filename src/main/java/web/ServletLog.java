package web;

import datos.UsuarioDaoJdbc;
import domain.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletLog")
public class ServletLog extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDaoJdbc userDao = new UsuarioDaoJdbc();
        int count = 0;
        String mensaje = "";

        String nombreU = request.getParameter("nUsuario");
        String pass = request.getParameter("password");

        Usuario user = new Usuario();

        user.setNombreUsuario(nombreU);
        user.setPassword(pass);

        try {
            count = userDao.loginUser(user);
            if (count > 0) {
                mensaje = "Login exitoso. ¡Bienvenido!";
            } else {
                mensaje = "Correo o contraseña incorrecta";
            }
        } catch (Exception e) {
            mensaje = "Acceso denegado: " + e.toString();
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios.jsp");
        request.setAttribute("message", mensaje);
        dispatcher.forward(request, response);

    }
}
