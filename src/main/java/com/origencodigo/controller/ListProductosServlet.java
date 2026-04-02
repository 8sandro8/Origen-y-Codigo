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

@WebServlet("/list-productos")
public class ListProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
        List<Producto> productos = productoDao.getAll();
        
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }
}
