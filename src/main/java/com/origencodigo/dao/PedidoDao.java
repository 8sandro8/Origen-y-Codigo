package com.origencodigo.dao;

import com.origencodigo.model.Pedido;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RegisterBeanMapper(Pedido.class)
public interface PedidoDao extends SqlObject {

    @SqlQuery("SELECT * FROM pedidos")
    List<Pedido> getAll();

    @SqlQuery("SELECT * FROM pedidos WHERE id = :id")
    Pedido getById(@Bind("id") int id);

    @SqlQuery("SELECT * FROM pedidos WHERE usuario_id = :usuarioId")
    List<Pedido> getByUsuario(@Bind("usuarioId") int usuarioId);

    @SqlUpdate("INSERT INTO pedidos (usuario_id, fecha_pedido, total, estado) VALUES (:usuarioId, :fechaPedido, :total, :estado)")
    @GetGeneratedKeys
    int add(@Bind("usuarioId") int usuarioId, @Bind("fechaPedido") LocalDateTime fechaPedido, @Bind("total") BigDecimal total, @Bind("estado") String estado);

    @SqlUpdate("UPDATE pedidos SET estado = :estado WHERE id = :id")
    int update(@Bind("id") int id, @Bind("estado") String estado);

    @SqlUpdate("DELETE FROM pedidos WHERE id = :id")
    int delete(@Bind("id") int id);
}
