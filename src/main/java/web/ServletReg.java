package web;

import datos.UsuarioDaoJdbc;
import domain.Usuario;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletReg")
public class ServletReg extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/registrarse.jsp");
        dispatcher.forward(request, response);
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UsuarioDaoJdbc userDao = new UsuarioDaoJdbc();
        int rowsAffected = 0;
        String mensaje = "";
        
        String nombre = request.getParameter("nombre");
        String pass = request.getParameter("pass");

        
        Usuario user = new Usuario();
        
        user.setNombreUsuario(nombre);
        user.setPassword(pass);
        
        try {
            rowsAffected = userDao.insertar(user);
            
            mensaje = "Usuario registrado satisfactoriamente";
            
        } catch (Exception e) {
            mensaje = "Error al registrar el usuario: " + e.toString();
            e.printStackTrace();
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios.jsp");
        request.setAttribute("message", mensaje);
        dispatcher.forward(request, response);
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
