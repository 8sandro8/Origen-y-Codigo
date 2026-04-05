package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Producto;
import com.origencodigo.model.ItemCarrito;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int productoId = Integer.parseInt(request.getParameter("id"));
        
        ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
        Producto producto = productoDao.getById(productoId);
        
        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        
        boolean encontrado = false;
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == productoId) {
                item.setCantidad(item.getCantidad() + 1);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            ItemCarrito nuevoItem = new ItemCarrito(producto, 1);
            carrito.add(nuevoItem);
        }
        
        session.setAttribute("carrito", carrito);
        
        // Detectar si es petición AJAX
        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        
        if (isAjax) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": true, \"totalItems\": " + carrito.size() + "}");
        } else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
