package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.UsuarioDao;
import com.origencodigo.model.Usuario;
import java.io.IOException;
import java.util.List;

@WebServlet("/list-usuarios")
public class ListUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
        List<Usuario> usuarios = usuarioDao.getAll();
        
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/views/list-usuarios.jsp").forward(request, response);
    }
}
