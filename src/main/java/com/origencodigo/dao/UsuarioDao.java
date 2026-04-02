package com.origencodigo.dao;

import com.origencodigo.model.Usuario;
import org.jdbi.v3.sqlobject.SqlQuery;
import org.jdbi.v3.sqlobject.SqlUpdate;
import org.jdbi.v3.sqlobject.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.annotation.Bind;
import org.jdbi.v3.sqlobject.annotation.SqlObject;
import java.util.List;

@RegisterBeanMapper(Usuario.class)
public interface UsuarioDao extends SqlObject {

    @SqlQuery("SELECT * FROM usuarios")
    List<Usuario> getAll();

    @SqlQuery("SELECT * FROM usuarios WHERE id = :id")
    Usuario getById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM usuarios WHERE email = :email")
    Usuario getByEmail(@Bind("email") String email);

    @SqlUpdate("INSERT INTO usuarios (nombre, email, contrasena, fecha_registro, es_admin) VALUES (:nombre, :email, :contrasena, :fechaRegistro, :esAdmin)")
    int add(@Bind("nombre") String nombre, @Bind("email") String email, @Bind("contrasena") String contrasena, @Bind("fechaRegistro") java.time.LocalDate fechaRegistro, @Bind("esAdmin") boolean esAdmin);

    @SqlUpdate("UPDATE usuarios SET nombre = :nombre, email = :email, contrasena = :contrasena, es_admin = :esAdmin WHERE id = :id")
    int update(@Bind("id") int id, @Bind("nombre") String nombre, @Bind("email") String email, @Bind("contrasena") String contrasena, @Bind("esAdmin") boolean esAdmin);

    @SqlUpdate("DELETE FROM usuarios WHERE id = :id")
    int delete(@Bind("id") int id);
}