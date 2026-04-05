package com.origencodigo.controller;

import com.origencodigo.dao.Database;
import com.origencodigo.dao.PedidoDao;
import com.origencodigo.dao.PedidoItemDao;
import com.origencodigo.model.Usuario;
import com.origencodigo.model.ItemCarrito;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // 1. Obtener lista deItemsCarrito de la sesión
        @SuppressWarnings("unchecked")
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        
        // 2. Si carrito vacío, redirigir a /cart con error
        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?error=carrito_vacio");
            return;
        }
        
        // 3. Obtener usuario de sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=/checkout");
            return;
        }
        
        try {
            // 4. Calcular total del carrito
            BigDecimal total = BigDecimal.ZERO;
            for (ItemCarrito item : carrito) {
                total = total.add(item.getSubtotal());
            }
            
            // 5. Insertar pedido en BBDD (estado: "pendiente")
            PedidoDao pedidoDao = Database.connect().onDemand(PedidoDao.class);
            int pedidoId = pedidoDao.add(usuario.getId(), LocalDateTime.now(), total, "pendiente");
            
            // 6. Insertar cada item en items_pedido
            PedidoItemDao pedidoItemDao = Database.connect().onDemand(PedidoItemDao.class);
            for (ItemCarrito item : carrito) {
                pedidoItemDao.add(
                    pedidoId,
                    item.getProducto().getId(),
                    item.getCantidad(),
                    item.getProducto().getPrecio()
                );
            }
            
            // 7. Vaciar carrito
            session.removeAttribute("carrito");
            
            // 8. Redirigir a /list-pedidos con mensaje éxito e ID del pedido
            response.sendRedirect(request.getContextPath() + "/list-pedidos?success=pedido_creado&pedidoId=" + pedidoId);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=checkout_fallido");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al carrito si alguien intenta acceder por GET
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
