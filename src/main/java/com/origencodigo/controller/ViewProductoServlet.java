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

@WebServlet("/detalle-producto")
public class ViewProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            Producto producto = productoDao.getById(id);
            
            if (producto == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            
            // Obtener productos recomendados de la misma categoría (excluyendo el actual)
            List<Producto> recomendados = productoDao.getRecomendados(producto.getCategoriaId(), id, 4);
            
            request.setAttribute("producto", producto);
            request.setAttribute("recomendados", recomendados);
            request.getRequestDispatcher("/WEB-INF/views/detalle-producto.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
