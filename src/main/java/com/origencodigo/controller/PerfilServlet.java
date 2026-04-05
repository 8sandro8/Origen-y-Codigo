package com.origencodigo.controller;

import com.origencodigo.dao.Database;
import com.origencodigo.dao.UsuarioDao;
import com.origencodigo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.regex.Pattern;

@WebServlet("/profile")
public class PerfilServlet extends HttpServlet {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

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
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        if ("actualizarPerfil".equals(accion)) {
            actualizarPerfil(request, response, usuario);
        } else if ("cambiarContrasena".equals(accion)) {
            cambiarContrasena(request, response, usuario);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        
        // Validaciones
        if (nombre == null || nombre.isBlank()) {
            request.setAttribute("error", "El nombre es obligatorio");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (email == null || email.isBlank()) {
            request.setAttribute("error", "El email es obligatorio");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            request.setAttribute("error", "Email no válido");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        try {
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            
            // Verificar si el email ya está en uso por otro usuario
            Usuario existente = usuarioDao.getByEmail(email);
            if (existente != null && existente.getId() != usuario.getId()) {
                request.setAttribute("error", "El email ya está en uso");
                request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
                return;
            }
            
            // Actualizar perfil
            usuarioDao.actualizarPerfil(usuario.getId(), nombre, email);
            
            // Actualizar en sesión
            HttpSession session = request.getSession();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            session.setAttribute("usuario", usuario);
            
            request.setAttribute("success", "Perfil actualizado correctamente");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
        }
    }

    private void cambiarContrasena(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws ServletException, IOException {
        
        String contrasenaActual = request.getParameter("contrasenaActual");
        String nuevaContrasena = request.getParameter("nuevaContrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");
        
        // Validaciones
        if (contrasenaActual == null || contrasenaActual.isBlank()) {
            request.setAttribute("error", "La contraseña actual es obligatoria");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (nuevaContrasena == null || nuevaContrasena.isBlank()) {
            request.setAttribute("error", "La nueva contraseña es obligatoria");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (confirmarContrasena == null || confirmarContrasena.isBlank()) {
            request.setAttribute("error", "Debe confirmar la nueva contraseña");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        if (nuevaContrasena.length() < 8) {
            request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            return;
        }
        
        try {
            // Verificar contraseña actual
            String contrasenaActualHash = hashPassword(contrasenaActual);
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            String contrasenaBBDD = usuarioDao.getContrasena(usuario.getId());
            
            if (!contrasenaBBDD.equals(contrasenaActualHash)) {
                request.setAttribute("error", "La contraseña actual no es correcta");
                request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
                return;
            }
            
            // Actualizar contraseña
            String nuevaContrasenaHash = hashPassword(nuevaContrasena);
            usuarioDao.actualizarContrasena(usuario.getId(), nuevaContrasenaHash);
            
            request.setAttribute("success", "Contraseña cambiada correctamente");
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/mi-perfil.jsp").forward(request, response);
        }
    }
}
