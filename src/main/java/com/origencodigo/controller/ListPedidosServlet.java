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
            
            // 2. Si es admin: listar todos los pedidos
            // 3. Si no es admin: listar solo pedidos del usuario
            if (usuario.isEsAdmin()) {
                pedidos = pedidoDao.getAll();
            } else {
                pedidos = pedidoDao.getByUsuario(usuario.getId());
            }
            
            request.setAttribute("pedidos", pedidos);
            request.getRequestDispatcher("/WEB-INF/views/pedidos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().print("Error en ListPedidosServlet: " + e.getMessage());
            response.setStatus(500);
        }
    }
}
