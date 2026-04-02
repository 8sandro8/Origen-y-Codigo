package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Producto;
import java.io.IOException;
import java.util.List;

@WebServlet("/search-productos")
public class SearchProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String origen = request.getParameter("origen");
        
        if (nombre == null) nombre = "";
        if (origen == null) origen = "";
        
        ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
        List<Producto> productos = productoDao.search(nombre, origen);
        
        request.setAttribute("productos", productos);
        request.setAttribute("searchNombre", nombre);
        request.setAttribute("searchOrigen", origen);
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }
}
