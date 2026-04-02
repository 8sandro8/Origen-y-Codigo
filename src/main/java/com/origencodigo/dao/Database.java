package com.origencodigo.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import java.io.InputStream;
import java.util.Properties;

public class Database {

    private static final String PROPERTIES_FILE = "/database.properties";
    private static final String DEFAULT_URL = "jdbc:mariadb://localhost:3306/origenycódigo";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    public static Jdbi jdbi;
    public static Handle db;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream is = Database.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            System.out.println("No se encontró archivo de propiedades, usando configuración por defecto");
        }

        String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : 
                     props.getProperty("db.url", DEFAULT_URL);
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : 
                      props.getProperty("db.user", DEFAULT_USER);
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : 
                          props.getProperty("db.password", DEFAULT_PASSWORD);

        jdbi = Jdbi.create(url, user, password);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public static Jdbi connect() {
        if (db == null || !db.isInTransaction()) {
            db = jdbi.open();
        }
        return jdbi;
    }

    public static void close() {
        if (db != null) {
            db.close();
        }
    }
}
