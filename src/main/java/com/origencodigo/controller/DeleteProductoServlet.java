package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Usuario;
import java.io.IOException;

@WebServlet("/delete-producto")
public class DeleteProductoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !usuario.isEsAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("productos?error=id_faltante");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            int result = productoDao.delete(id);

            // Detectar si es petición AJAX
            String ajaxHeader = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

            if (isAjax) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if (result > 0) {
                    response.getWriter().write("{\"success\": true, \"message\": \"Producto eliminado\"}");
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Producto no encontrado\"}");
                }
                return;
            }

            if (result > 0) {
                response.sendRedirect("productos?success=borrado");
            } else {
                response.sendRedirect("productos?error=no_encontrado");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("productos?error=id_invalido");
        } catch (Exception e) {
            // Programación defensiva: capturar restricción de FK o cualquier otra excepción
            String msg = e.getMessage();

            // Detectar si es petición AJAX
            String ajaxHeader = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

            if (isAjax) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if (msg != null && (msg.toLowerCase().contains("foreign key") || msg.toLowerCase().contains("constraint"))) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Producto en uso\"}");
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Error desconocido\"}");
                }
                return;
            }

            if (msg != null && (msg.toLowerCase().contains("foreign key") || msg.toLowerCase().contains("constraint"))) {
                response.sendRedirect("productos?error=en_uso");
            } else {
                e.printStackTrace();
                response.sendRedirect("productos?error=desconocido");
            }
        }
    }
}
