package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.UsuarioDao;
import com.origencodigo.model.Usuario;
import java.io.IOException;

@WebServlet("/delete-usuario")
public class DeleteUsuarioServlet extends HttpServlet {

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
            response.sendRedirect("usuarios?error=id_faltante");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            UsuarioDao usuarioDao = Database.connect().onDemand(UsuarioDao.class);
            int result = usuarioDao.delete(id);
            
            if (result > 0) {
                response.sendRedirect("usuarios?success=borrado");
            } else {
                response.sendRedirect("usuarios?error=no_encontrado");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("usuarios?error=id_invalido");
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && (msg.toLowerCase().contains("foreign key") || msg.toLowerCase().contains("constraint"))) {
                response.sendRedirect("usuarios?error=en_uso");
            } else {
                e.printStackTrace();
                response.sendRedirect("usuarios?error=desconocido");
            }
        }
    }
}
