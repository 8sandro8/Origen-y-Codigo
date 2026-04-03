package com.origencodigo.dao;

import com.origencodigo.model.Usuario;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import java.util.List;
import java.time.LocalDate;

@RegisterBeanMapper(Usuario.class)
public interface UsuarioDao extends SqlObject {

    @SqlQuery("SELECT * FROM usuarios")
    List<Usuario> getAll();

    @SqlQuery("SELECT * FROM usuarios WHERE id = :id")
    Usuario getById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM usuarios WHERE email = :email")
    Usuario getByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM usuarios WHERE email = :email AND contrasena = :contrasenaHash")
    Usuario verificarContrasena(@Bind("email") String email, @Bind("contrasenaHash") String contrasenaHash);

    @SqlUpdate("UPDATE usuarios SET contrasena = :contrasena WHERE id = :id")
    int actualizarContrasena(@Bind("id") int id, @Bind("contrasena") String contrasena);

    @SqlUpdate("INSERT INTO usuarios (nombre, email, contrasena, fecha_registro, es_admin) VALUES (:nombre, :email, :contrasena, :fechaRegistro, :esAdmin)")
    int add(@Bind("nombre") String nombre, @Bind("email") String email, @Bind("contrasena") String contrasena, @Bind("fechaRegistro") LocalDate fechaRegistro, @Bind("esAdmin") boolean esAdmin);

    @SqlUpdate("UPDATE usuarios SET nombre = :nombre, email = :email, contrasena = :contrasena, es_admin = :esAdmin WHERE id = :id")
    int update(@Bind("id") int id, @Bind("nombre") String nombre, @Bind("email") String email, @Bind("contrasena") String contrasena, @Bind("esAdmin") boolean esAdmin);

    @SqlUpdate("DELETE FROM usuarios WHERE id = :id")
    int delete(@Bind("id") int id);
}
