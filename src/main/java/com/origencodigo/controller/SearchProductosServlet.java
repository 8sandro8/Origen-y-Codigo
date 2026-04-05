package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search-productos")
public class SearchProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String origen = request.getParameter("origen");
        boolean isAjax = "true".equals(request.getParameter("ajax"));
        
        if (nombre == null) nombre = "";
        if (origen == null) origen = "";
        
        ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
        List<Producto> productos = productoDao.search(nombre, origen);
        
        if (isAjax) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            if (productos.isEmpty()) {
                out.print("<div class='col-12 text-center py-5'>");
                out.print("<i class='bi bi-cup-hot text-muted' style='font-size: 4rem;'></i>");
                out.print("<p class='text-muted mt-3 fs-5'>No se encontraron productos.</p>");
                out.print("</div>");
            } else {
                for (Producto p : productos) {
                    out.print("<div class='col-12 col-md-6 col-lg-4'>");
                    out.print("<div class='card h-100 shadow-sm'>");
                    out.print("<a href='" + request.getContextPath() + "/detalle-producto?id=" + p.getId() + "' class='text-decoration-none d-block h-100'>");
                    
                    if (p.getImagenUrl() != null && !p.getImagenUrl().isEmpty()) {
                        String imagenUrl = p.getImagenUrl();
                        // Limpiar barras extra al inicio
                        if (imagenUrl.startsWith("/")) {
                            imagenUrl = imagenUrl.substring(1);
                        }
                        out.print("<img src='" + request.getContextPath() + "/uploads/" + imagenUrl + "' class='card-img-top' alt='" + p.getNombre() + "' style='height: 200px; width: 100%; object-fit: cover;'>");
                    } else {
                        out.print("<div class='d-flex align-items-center justify-content-center bg-secondary' style='height: 200px;'>");
                        out.print("<i class='bi bi-cup-hot fs-1 text-white'></i>");
                        out.print("</div>");
                    }
                    
                    out.print("<div class='card-body'>");
                    out.print("<h5 class='card-title mb-2 text-dark'>" + p.getNombre() + "</h5>");
                    out.print("<p class='card-text small text-muted mb-3'>" + (p.getDescripcion() != null ? p.getDescripcion() : "") + "</p>");
                    out.print("<div class='d-flex justify-content-between align-items-center mb-3'>");
                    out.print("<span class='badge bg-secondary'><i class='bi bi-geo-alt-fill'></i> " + p.getOrigen() + "</span>");
                    out.print("<span class='badge' style='background: linear-gradient(135deg, #D2691E, #8B4513);'>" + p.getPrecio().setScale(2) + " €</span>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</a>");
                    
                    out.print("<div class='card-footer bg-white border-top-0'>");
                    if (p.isStockDisponible()) {
                        out.print("<button type='button' class='btn btn-warning w-100 fw-bold btn-anadir' data-id='" + p.getId() + "'>");
                        out.print("<i class='bi bi-cart-plus me-2'></i>Añadir</button>");
                    } else {
                        out.print("<button class='btn btn-secondary w-100 fw-bold' disabled><i class='bi bi-x-circle me-2'></i>Sin stock</button>");
                    }
                    out.print("</div>");
                    
                    out.print("</div>");
                    out.print("</div>");
                }
            }
            return;
        }
        
        request.setAttribute("productos", productos);
        request.setAttribute("searchNombre", nombre);
        request.setAttribute("searchOrigen", origen);
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }
}