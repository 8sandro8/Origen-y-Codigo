package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.CategoriaDao;
import com.origencodigo.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

@WebServlet("/delete-categoria")
public class DeleteCategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !usuario.isEsAdmin()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(new Response(false, "No autorizado")));
            return;
        }
        
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
            CategoriaDao categoriaDao = Database.connect().onDemand(CategoriaDao.class);
            int result = categoriaDao.delete(id);
            
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.print(new Gson().toJson(new Response(true, "Categoría eliminada correctamente")));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(new Gson().toJson(new Response(false, "Categoría no encontrada")));
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
