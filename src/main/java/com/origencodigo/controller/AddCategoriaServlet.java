package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.CategoriaDao;
import java.io.IOException;

@WebServlet("/add-categoria")
public class AddCategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/add-categoria.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        
        try {
            CategoriaDao categoriaDao = Database.connect().onDemand(CategoriaDao.class);
            categoriaDao.add(nombre, descripcion);
            
            response.sendRedirect(request.getContextPath() + "/list-categorias");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al agregar categoría: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/add-categoria.jsp").forward(request, response);
        }
    }
}