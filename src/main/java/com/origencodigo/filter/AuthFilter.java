package com.origencodigo.filter;

import com.origencodigo.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
        "/", "/login", "/logout", "/list-productos", "/search-productos", 
        "/view-producto", "/index.jsp", "/static", "/WEB-INF/views/",
        "/list-pedidos", "/checkout", "/add-to-cart", "/remove-from-cart", "/cart",
        "/detalle-pedido", "/profile", "/mi-perfil"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        HttpSession session = request.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario") : null;
        
        if (usuario == null) {
            response.sendRedirect(contextPath + "/login");
            return;
        }
        
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.equals(publicPath) || path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }
}