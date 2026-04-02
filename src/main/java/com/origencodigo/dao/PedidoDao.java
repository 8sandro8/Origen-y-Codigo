package com.origencodigo.dao;

import com.origencodigo.model.Pedido;
import org.jdbi.v3.sqlobject.SqlQuery;
import org.jdbi.v3.sqlobject.SqlUpdate;
import org.jdbi.v3.sqlobject.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.annotation.Bind;
import org.jdbi.v3.sqlobject.annotation.SqlObject;
import java.util.List;

@RegisterBeanMapper(Pedido.class)
public interface PedidoDao extends SqlObject {

    @SqlQuery("SELECT * FROM pedidos")
    List<Pedido> getAll();

    @SqlQuery("SELECT * FROM pedidos WHERE id = :id")
    Pedido getById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM pedidos WHERE usuario_id = :usuarioId")
    List<Pedido> getByUsuario(@Bind("usuarioId") int usuarioId);

    @SqlUpdate("INSERT INTO pedidos (usuario_id, fecha_pedido, total, estado) VALUES (:usuarioId, :fechaPedido, :total, :estado)")
    int add(@Bind("usuarioId") int usuarioId, @Bind("fechaPedido") java.time.LocalDateTime fechaPedido, @Bind("total") java.math.BigDecimal total, @Bind("estado") String estado);

    @SqlUpdate("UPDATE pedidos SET estado = :estado WHERE id = :id")
    int update(@Bind("id") int id, @Bind("estado") String estado);

    @SqlUpdate("DELETE FROM pedidos WHERE id = :id")
    int delete(@Bind("id") int id);
}