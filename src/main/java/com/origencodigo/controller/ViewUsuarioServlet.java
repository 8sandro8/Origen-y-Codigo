package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.UsuarioDao;
import com.origencodigo.model.Usuario;
import java.io.IOException;

@WebServlet("/view-usuario")
public class ViewUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que es Admin
        HttpSession session = request.getSession();
        Usuario usuarioActual = (Usuario) session.getAttribute("usuario");
        
        if (usuarioActual == null || !usuarioActual.isEsAdmin()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/list-usuarios");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            Usuario usuario = usuarioDao.getById(id);
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/list-usuarios");
                return;
            }
            
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/WEB-INF/views/view-usuario.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error en ViewUsuarioServlet: " + e.getMessage());
            response.setStatus(500);
        }
    }
}