package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.model.ItemCarrito;
import java.io.IOException;
import java.util.List;
import java.math.BigDecimal;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        
        if (carrito == null) {
            response.setStatus(400);
            response.getWriter().write("{\"error\": \"Carrito vacío\"}");
            return;
        }
        
        String productoIdParam = request.getParameter("id");
        if (productoIdParam == null || productoIdParam.isBlank()) {
            response.setStatus(400);
            response.getWriter().write("{\"error\": \"ID de producto requerido\"}");
            return;
        }
        
        try {
            int productoId = Integer.parseInt(productoIdParam);
            
            // Buscar y eliminar el producto del carrito
            boolean eliminado = false;
            for (int i = 0; i < carrito.size(); i++) {
                if (carrito.get(i).getProducto().getId() == productoId) {
                    carrito.remove(i);
                    eliminado = true;
                    break;
                }
            }
            
            if (!eliminado) {
                response.setStatus(404);
                response.getWriter().write("{\"error\": \"Producto no encontrado en el carrito\"}");
                return;
            }
            
            // Calcular nuevo total
            BigDecimal nuevoTotal = BigDecimal.ZERO;
            int nuevoTotalItems = 0;
            for (ItemCarrito item : carrito) {
                nuevoTotal = nuevoTotal.add(item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
                nuevoTotalItems += item.getCantidad();
            }
            
            session.setAttribute("carrito", carrito);
            
            // Devolver JSON
            response.getWriter().write("{\"success\": true, \"totalItems\": " + nuevoTotalItems + ", \"total\": \"" + nuevoTotal.setScale(2) + "\"}");
            
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"error\": \"ID de producto inválido\"}");
        }
    }
}