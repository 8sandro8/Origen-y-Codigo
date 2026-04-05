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
import java.sql.SQLException;

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
            
            if (result > 0) {
                response.sendRedirect("productos?success=borrado");
            } else {
                response.sendRedirect("productos?error=no_encontrado");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("productos?error=id_invalido");
        } catch (SQLException e) {
            // Programación defensiva: capturar restricción de FK
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key") || msg.contains("constraint")) {
                response.sendRedirect("productos?error=en_uso");
            } else {
                e.printStackTrace();
                response.sendRedirect("productos?error=bd");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("productos?error=desconocido");
        }
    }
}
