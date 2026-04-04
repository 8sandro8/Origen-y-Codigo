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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

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
            // Generar nombre único
            String extension = getFileExtension(Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString());
            String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
            
            // Obtener directorio de uploads desde variable de entorno o defecto
            String uploadDir = System.getenv("UPLOAD_DIR");
            if (uploadDir == null) {
                String catalinaHome = System.getProperty("catalina.base", "/usr/local/tomcat");
                uploadDir = catalinaHome + "/webapps/uploads";
            }
            
            Path uploadsPath = Paths.get(uploadDir);
            
            // Crear directorio si no existe
            if (!Files.exists(uploadsPath)) {
                Files.createDirectories(uploadsPath);
            }
            
            // Comprimir y redimensionar imagen (800x800, 80% calidad)
            Path tempFile = uploadsPath.resolve("temp_" + uniqueFileName);
            Path outputFile = uploadsPath.resolve(uniqueFileName);
            
            try (InputStream input = imagenPart.getInputStream()) {
                Thumbnails.of(input)
                    .size(800, 800)
                    .outputFormat("jpg")
                    .outputQuality(0.8)
                    .toFile(tempFile.toFile());
                
                // Mover archivo definitivo
                Files.move(tempFile, outputFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                // Si falla la compresión, guardar original
                try (InputStream input = imagenPart.getInputStream()) {
                    Files.copy(input, outputFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
            }
            
            // Guardar referencia en BBDD (ruta relativa al servlet de imágenes)
            imagenUrl = uniqueFileName;
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
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg";
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot == -1) {
            return "jpg";
        }
        String ext = fileName.substring(lastDot + 1).toLowerCase();
        // Asegurar formato válido
        if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("webp")) {
            return ext;
        }
        return "jpg";
    }
}
