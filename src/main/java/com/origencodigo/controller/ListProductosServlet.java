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

    private static final int PRODUCTOS_POR_PAGINA = 12;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
        
        // Obtener página actual (por defecto 1)
        int paginaActual = 1;
        String paginaParam = request.getParameter("page");
        if (paginaParam != null && !paginaParam.isBlank()) {
            try {
                paginaActual = Integer.parseInt(paginaParam);
                if (paginaActual < 1) paginaActual = 1;
            } catch (NumberFormatException e) {
                paginaActual = 1;
            }
        }
        
        // Calcular offset
        int offset = (paginaActual - 1) * PRODUCTOS_POR_PAGINA;
        
        // Obtener productos paginados y total
        List<Producto> productos = productoDao.getAllPaginated(PRODUCTOS_POR_PAGINA, offset);
        int totalProductos = productoDao.getTotalCount();
        int totalPaginas = (int) Math.ceil((double) totalProductos / PRODUCTOS_POR_PAGINA);
        
        // Atributos para la vista
        request.setAttribute("productos", productos);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("totalProductos", totalProductos);
        
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }
}
