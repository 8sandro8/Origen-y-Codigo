package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.UsuarioDao;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.LocalDate;

@WebServlet("/add-usuario")
public class AddUsuarioServlet extends HttpServlet {

    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/add-usuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasenaPlana = request.getParameter("contrasena");
        
        try {
            String contrasenaHash = hashPassword(contrasenaPlana);
            LocalDate fechaRegistro = LocalDate.now();
            boolean esAdmin = false;
            
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            usuarioDao.add(nombre, email, contrasenaHash, fechaRegistro, esAdmin);
            
            response.sendRedirect(request.getContextPath() + "/list-usuarios");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al agregar usuario: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/add-usuario.jsp").forward(request, response);
        }
    }
}