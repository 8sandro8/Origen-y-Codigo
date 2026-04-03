package com.origencodigo.dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class Database {

    public static Jdbi jdbi;
    public static Handle db;

    public static Jdbi connect() {
        try {
            // Carga explícita del driver MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
            // Conexión al NAS Synology
            jdbi = Jdbi.create("jdbc:mariadb://db:3306/origen_codigo", "root", "rootpassword");
            jdbi.installPlugin(new SqlObjectPlugin());
            db = jdbi.open();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MariaDB no encontrado", e);
        }
        return jdbi;
    }

    public static void close() {
        if (db != null) {
            db.close();
        }
    }
}
