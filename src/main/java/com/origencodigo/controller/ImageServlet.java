package com.origencodigo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String imagenNombre = request.getPathInfo();
        
        if (imagenNombre == null || imagenNombre.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Quitar el "/" inicial
        imagenNombre = imagenNombre.substring(1);
        
        // Ruta donde se guardan las imágenes
        String uploadDir = System.getenv("UPLOAD_DIR");
        if (uploadDir == null) {
            // Valor por defecto para desarrollo local
            String catalinaHome = System.getProperty("catalina.base", "/usr/local/tomcat");
            uploadDir = catalinaHome + "/webapps/uploads";
        }
        
        Path imagePath = Paths.get(uploadDir, imagenNombre);
        
        if (!Files.exists(imagePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Determinar el tipo MIME
        String mimeType = Files.probeContentType(imagePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + imagenNombre + "\"");
        
        // Escribir la imagen
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(imagePath, out);
        }
    }
}