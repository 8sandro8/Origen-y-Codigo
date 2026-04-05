package com.origencodigo.filter;

import com.origencodigo.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
        "/", "/login", "/logout", "/list-productos", "/search-productos", 
        "/view-producto", "/index.jsp", "/static", "/WEB-INF/views/",
        "/list-pedidos", "/checkout", "/add-to-cart", "/remove-from-cart", "/cart",
        "/detalle-pedido", "/profile", "/mi-perfil"
    };
    
    // Patrones para recursos estáticos que siempre son públicos
    private static final Pattern STATIC_RESOURCE_PATTERN = Pattern.compile(
        ".*(\\.(png|jpg|jpeg|gif|css|js|ico|woff|woff2|ttf|eot|svg|webp)$|/uploads/|/assets/|/images/)",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        
        // Si es un recurso estático, permitir siempre
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }
        
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

    private boolean isStaticResource(String path) {
        return STATIC_RESOURCE_PATTERN.matcher(path).matches();
    }
    
    private boolean isPublicPath(String path) {
        // Normalizar path: quitar barras trailing
        String normalizedPath = path.endsWith("/") && path.length() > 1 ? path.substring(0, path.length() - 1) : path;
        
        for (String publicPath : PUBLIC_PATHS) {
            String normalizedPublic = publicPath.endsWith("/") && publicPath.length() > 1 ? publicPath.substring(0, publicPath.length() - 1) : publicPath;
            if (normalizedPath.equals(normalizedPublic) || normalizedPath.startsWith(normalizedPublic + "/")) {
                return true;
            }
        }
        return false;
    }
}