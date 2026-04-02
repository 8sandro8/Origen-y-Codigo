package com.origencodigo.dao;

import com.origencodigo.model.Producto;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.annotation.SqlObject;
import java.util.List;
import java.math.BigDecimal;

@RegisterBeanMapper(Producto.class)
public interface ProductoDao extends SqlObject {

    @SqlQuery("SELECT * FROM productos")
    List<Producto> getAll();

    @SqlQuery("SELECT * FROM productos WHERE id = :id")
    Producto getById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM productos WHERE categoria_id = :categoriaId")
    List<Producto> getByCategoria(@Bind("categoriaId") int categoriaId);

    @SqlQuery("SELECT * FROM productos WHERE nombre LIKE CONCAT('%', :nombre, '%') AND origen LIKE CONCAT('%', :origen, '%')")
    List<Producto> search(@Bind("nombre") String nombre, @Bind("origen") String origen);

    @SqlUpdate("INSERT INTO productos (categoria_id, nombre, descripcion, precio, origen, imagen_url, stock_disponible) VALUES (:categoriaId, :nombre, :descripcion, :precio, :origen, :imagenUrl, :stockDisponible)")
    int add(@Bind("categoriaId") int categoriaId, @Bind("nombre") String nombre, @Bind("descripcion") String descripcion, @Bind("precio") BigDecimal precio, @Bind("origen") String origen, @Bind("imagenUrl") String imagenUrl, @Bind("stockDisponible") boolean stockDisponible);

    @SqlUpdate("UPDATE productos SET categoria_id = :categoriaId, nombre = :nombre, descripcion = :descripcion, precio = :precio, origen = :origen, imagen_url = :imagenUrl, stock_disponible = :stockDisponible WHERE id = :id")
    int update(@Bind("id") int id, @Bind("categoriaId") int categoriaId, @Bind("nombre") String nombre, @Bind("descripcion") String descripcion, @Bind("precio") BigDecimal precio, @Bind("origen") String origen, @Bind("imagenUrl") String imagenUrl, @Bind("stockDisponible") boolean stockDisponible);

    @SqlUpdate("DELETE FROM productos WHERE id = :id")
    int delete(@Bind("id") int id);
}
