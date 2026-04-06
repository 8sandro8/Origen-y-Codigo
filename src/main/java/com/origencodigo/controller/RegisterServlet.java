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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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
        
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasenaPlana = request.getParameter("contrasena");
        
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank() 
                || contrasenaPlana == null || contrasenaPlana.isBlank()) {
            request.setAttribute("error", "Todos los campos son obligatorios");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        try {
            // Verificar si el email ya existe
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            var usuarioExistente = usuarioDao.getByEmail(email);
            
            if (usuarioExistente != null) {
                request.setAttribute("error", "El email ya está registrado");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }
            
            String contrasenaHash = hashPassword(contrasenaPlana);
            LocalDate fechaRegistro = LocalDate.now();
            boolean esAdmin = false;
            
            usuarioDao.add(nombre, email, contrasenaHash, fechaRegistro, esAdmin);
            
            // Redirigir al login con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/login?success=registrado");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al registrar: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
