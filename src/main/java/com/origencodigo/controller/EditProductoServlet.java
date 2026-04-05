package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import com.origencodigo.dao.Database;
import com.origencodigo.dao.ProductoDao;
import com.origencodigo.model.Producto;
import com.origencodigo.model.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;

@WebServlet("/edit-producto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class EditProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que es Admin
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !usuario.isEsAdmin()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/list-productos");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            Producto producto = productoDao.getById(id);
            
            if (producto == null) {
                response.sendRedirect(request.getContextPath() + "/list-productos");
                return;
            }
            
            request.setAttribute("producto", producto);
            request.getRequestDispatcher("/WEB-INF/views/edit-producto.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/list-productos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que es Admin
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !usuario.isEsAdmin()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");
        String origen = request.getParameter("origen");
        String categoriaIdStr = request.getParameter("categoriaId");
        String stockStr = request.getParameter("stockDisponible");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/list-productos");
            return;
        }
        
        try {
            int id = Integer.parseInt(idParam);
            int categoriaId = Integer.parseInt(categoriaIdStr);
            BigDecimal precio = new BigDecimal(precioStr);
            boolean stock = "on".equals(stockStr) || "true".equals(stockStr);
            
            ProductoDao productoDao = Database.connect().onDemand(ProductoDao.class);
            
            // Obtener producto actual para mantener la imagen si no se sube una nueva
            Producto productoActual = productoDao.getById(id);
            String imagenUrl = productoActual.getImagenUrl();
            
            // Procesar nueva imagen si se subió
            Part imagenPart = request.getPart("imagen");
            if (imagenPart != null && imagenPart.getSize() > 0) {
                String extension = getFileExtension(Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString());
                String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
                
                String uploadDir = System.getenv("UPLOAD_DIR");
                if (uploadDir == null) {
                    String catalinaHome = System.getProperty("catalina.base", "/usr/local/tomcat");
                    uploadDir = catalinaHome + "/webapps/uploads";
                }
                
                Path uploadsPath = Paths.get(uploadDir);
                if (!Files.exists(uploadsPath)) {
                    Files.createDirectories(uploadsPath);
                }
                
                Path tempFile = uploadsPath.resolve("temp_" + uniqueFileName);
                Path outputFile = uploadsPath.resolve(uniqueFileName);
                
                try (InputStream input = imagenPart.getInputStream()) {
                    Thumbnails.of(input)
                        .size(800, 800)
                        .outputFormat("jpg")
                        .outputQuality(0.8)
                        .toFile(tempFile.toFile());
                    
                    Files.move(tempFile, outputFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    try (InputStream input = imagenPart.getInputStream()) {
                        Files.copy(input, outputFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                
                imagenUrl = uniqueFileName;
            }
            
            productoDao.update(id, categoriaId, nombre, descripcion, precio, origen, imagenUrl, stock);
            
            response.sendRedirect(request.getContextPath() + "/list-productos");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar producto: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/edit-producto.jsp").forward(request, response);
        }
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg";
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot == -1) {
            return "jpg";
        }
        String ext = fileName.substring(lastDot + 1).toLowerCase();
        if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("webp")) {
            return ext;
        }
        return "jpg";
    }
}