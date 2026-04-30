package com.origencodigo.controller;

import com.origencodigo.dao.Database;
import com.origencodigo.dao.PedidoDao;
import com.origencodigo.model.Usuario;
import com.origencodigo.model.Pedido;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/list-pedidos")
public class ListPedidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            PedidoDao pedidoDao = Database.connect().onDemand(PedidoDao.class);
            List<Pedido> pedidos;
            String titulo = "Mis Pedidos";

            // Si viene usuarioId por parametro (desde vista usuario), mostrar pedidos de ese usuario
            String usuarioIdParam = request.getParameter("usuarioId");
            if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                // Admin quiere ver pedidos de un usuario especifico
                int usuarioIdTarget = Integer.parseInt(usuarioIdParam);
                pedidos = pedidoDao.getByUsuario(usuarioIdTarget);
                titulo = "Pedidos del Usuario";
            } else if (usuario.isEsAdmin()) {
                // Admin sin parametro → todos los pedidos
                pedidos = pedidoDao.getAll();
                titulo = "Todos los Pedidos";
            } else {
                // Cliente sin parametro → solo sus pedidos
                pedidos = pedidoDao.getByUsuario(usuario.getId());
                titulo = "Mis Pedidos";
            }

            request.setAttribute("pedidos", pedidos);
            request.setAttribute("titulo", titulo);
            request.getRequestDispatcher("/WEB-INF/views/pedidos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().print("Error en ListPedidosServlet: " + e.getMessage());
            response.setStatus(500);
        }
    }
}
