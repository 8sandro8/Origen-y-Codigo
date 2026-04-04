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
import java.util.List;

@WebServlet("/search-usuarios")
public class SearchUsuarioServlet extends HttpServlet {

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
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        
        UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
        List<Usuario> usuarios;
        
        // Si hay criterios de búsqueda, usar search; si no, listar todos
        if ((nombre != null && !nombre.isBlank()) || (email != null && !email.isBlank())) {
            usuarios = usuarioDao.search(
                nombre != null ? nombre : "",
                email != null ? email : ""
            );
        } else {
            usuarios = usuarioDao.getAll();
        }
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("busquedaNombre", nombre);
        request.setAttribute("busquedaEmail", email);
        request.getRequestDispatcher("/WEB-INF/views/list-usuarios.jsp").forward(request, response);
    }
}