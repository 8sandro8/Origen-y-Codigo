package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.CategoriaDao;
import com.origencodigo.model.Categoria;
import java.io.IOException;
import java.util.List;

@WebServlet("/list-categorias")
public class ListCategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CategoriaDao categoriaDao = Database.connect().onDemand(CategoriaDao.class);
        List<Categoria> categorias = categoriaDao.getAll();
        
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("/WEB-INF/views/list-categorias.jsp").forward(request, response);
    }
}
