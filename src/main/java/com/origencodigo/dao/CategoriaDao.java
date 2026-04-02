package com.origencodigo.dao;

import com.origencodigo.model.Categoria;
import org.jdbi.v3.sqlobject.SqlQuery;
import org.jdbi.v3.sqlobject.SqlUpdate;
import org.jdbi.v3.sqlobject.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.annotation.Bind;
import org.jdbi.v3.sqlobject.annotation.SqlObject;
import java.util.List;

@RegisterBeanMapper(Categoria.class)
public interface CategoriaDao extends SqlObject {

    @SqlQuery("SELECT * FROM categorias")
    List<Categoria> getAll();

    @SqlQuery("SELECT * FROM categorias WHERE id = :id")
    Categoria getById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO categorias (nombre, descripcion) VALUES (:nombre, :descripcion)")
    int add(@Bind("nombre") String nombre, @Bind("descripcion") String descripcion);

    @SqlUpdate("UPDATE categorias SET nombre = :nombre, descripcion = :descripcion WHERE id = :id")
    int update(@Bind("id") int id, @Bind("nombre") String nombre, @Bind("descripcion") String descripcion);

    @SqlUpdate("DELETE FROM categorias WHERE id = :id")
    int delete(@Bind("id") int id);
}