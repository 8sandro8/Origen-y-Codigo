package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.CategoriaDao;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Categoria;
import com.origencodigo.model.Producto;
import java.io.IOException;
import java.util.List;

@WebServlet("/view-categoria")
public class ViewCategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/list-categorias");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            CategoriaDao categoriaDao = Database.connect().onDemand(CategoriaDao.class);
            Categoria categoria = categoriaDao.getById(id);
            
            if (categoria == null) {
                response.sendRedirect(request.getContextPath() + "/list-categorias");
                return;
            }
            
            // Obtener productos de esta categoría
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            List<Producto> productos = productoDao.getByCategoria(id);
            
            request.setAttribute("categoria", categoria);
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("/WEB-INF/views/view-categoria.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/list-categorias");
        }
    }
}