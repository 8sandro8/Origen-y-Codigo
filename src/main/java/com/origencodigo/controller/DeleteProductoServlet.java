package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

@WebServlet("/delete-producto")
public class DeleteProductoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(new Response(false, "ID no proporcionado")));
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            int result = productoDao.delete(id);
            
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.print(new Gson().toJson(new Response(true, "Producto eliminado correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(new Gson().toJson(new Response(false, "Producto no encontrado")));
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(new Response(false, "ID inválido")));
        }
    }
    
    private static class Response {
        public boolean success;
        public String message;
        
        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
