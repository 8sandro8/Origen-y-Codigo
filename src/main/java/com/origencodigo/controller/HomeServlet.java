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

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Paginación: 8 productos por página
        int pagina = 1;
        try {
            String paginaParam = request.getParameter("pagina");
            if (paginaParam != null && !paginaParam.isEmpty()) {
                pagina = Integer.parseInt(paginaParam);
            }
        } catch (NumberFormatException e) {
            pagina = 1;
        }
        
        int productosPorPagina = 8;
        int offset = (pagina - 1) * productosPorPagina;
        
        try {
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            List<Producto> productos = productoDao.getAll();
            
            // Calcular paginación
            int totalProductos = productos.size();
            int totalPaginas = (int) Math.ceil((double) totalProductos / productosPorPagina);
            
            // Obtener solo los productos de la página actual
            int endIndex = Math.min(offset + productosPorPagina, totalProductos);
            List<Producto> productosPagina = productos.subList(offset, endIndex);
            
            request.setAttribute("productos", productosPagina);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("totalProductos", totalProductos);
            
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los productos: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}