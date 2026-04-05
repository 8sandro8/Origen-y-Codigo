package com.origencodigo.controller;

import com.origencodigo.dao.Database;
import com.origencodigo.dao.PedidoDao;
import com.origencodigo.dao.PedidoItemDao;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Usuario;
import com.origencodigo.model.Pedido;
import com.origencodigo.model.PedidoItem;
import com.origencodigo.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/detalle-pedido")
public class DetallePedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pedidoIdParam = request.getParameter("id");
        if (pedidoIdParam == null || pedidoIdParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/list-pedidos");
            return;
        }
        
        try {
            int pedidoId = Integer.parseInt(pedidoIdParam);
            
            PedidoDao pedidoDao = Database.connect().onDemand(PedidoDao.class);
            Pedido pedido = pedidoDao.getById(pedidoId);
            
            if (pedido == null) {
                response.sendRedirect(request.getContextPath() + "/list-pedidos");
                return;
            }
            
            // Verificar permiso: admin puede ver todos, usuario solo los suyos
            if (!usuario.isEsAdmin() && pedido.getUsuarioId() != usuario.getId()) {
                response.sendRedirect(request.getContextPath() + "/list-pedidos");
                return;
            }
            
            // Obtener items del pedido
            PedidoItemDao pedidoItemDao = Database.connect().onDemand(PedidoItemDao.class);
            List<PedidoItem> items = pedidoItemDao.getByPedido(pedidoId);
            
            // Obtener datos de productos para mostrar nombre
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            List<Producto> productos = new ArrayList<>();
            if (items != null) {
                for (PedidoItem item : items) {
                    Producto producto = productoDao.getById(item.getProductoId());
                    productos.add(producto);
                }
            }
            
            request.setAttribute("pedido", pedido);
            request.setAttribute("items", items);
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("/WEB-INF/views/detalle-pedido.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().print("Error en DetallePedidoServlet: " + e.getMessage());
            response.setStatus(500);
        }
    }
}