package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.math.BigDecimal;

@WebServlet("/add-producto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class AddProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/add-producto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");
        String origen = request.getParameter("origen");
        String categoriaIdStr = request.getParameter("categoriaId");
        
        String imagenUrl = null;
        Part imagenPart = request.getPart("imagen");
        
        if (imagenPart != null && imagenPart.getSize() > 0) {
            String fileName = Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            
            String uploadPath = getServletContext().getRealPath("/images");
            Path uploadDir = Paths.get(uploadPath);
            
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            Path filePath = uploadDir.resolve(uniqueFileName);
            try (InputStream input = imagenPart.getInputStream()) {
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            imagenUrl = "/images/" + uniqueFileName;
        }
        
        try {
            int categoriaId = Integer.parseInt(categoriaIdStr);
            BigDecimal precio = new BigDecimal(precioStr);
            
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            productoDao.add(categoriaId, nombre, descripcion, precio, origen, imagenUrl, true);
            
            response.sendRedirect(request.getContextPath() + "/list-productos");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al agregar producto: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/add-producto.jsp").forward(request, response);
        }
    }
}
