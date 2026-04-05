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

@WebServlet("/search-categorias")
public class SearchCategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        
        CategoriaDao categoriaDao = Database.connect().onDemand(CategoriaDao.class);
        List<Categoria> categorias;
        
        // Si hay criterios de búsqueda, usar search; si no, listar todos
        if ((nombre != null && !nombre.isBlank()) || (descripcion != null && !descripcion.isBlank())) {
            categorias = categoriaDao.search(
                nombre != null ? nombre : "",
                descripcion != null ? descripcion : ""
            );
        } else {
            categorias = categoriaDao.getAll();
        }
        
        request.setAttribute("categorias", categorias);
        request.setAttribute("busquedaNombre", nombre);
        request.setAttribute("busquedaDescripcion", descripcion);
        request.getRequestDispatcher("/WEB-INF/views/list-categorias.jsp").forward(request, response);
    }
}