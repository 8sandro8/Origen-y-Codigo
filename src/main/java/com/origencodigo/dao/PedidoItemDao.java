package com.origencodigo.dao;

import com.origencodigo.model.PedidoItem;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import java.util.List;
import java.math.BigDecimal;

@RegisterBeanMapper(PedidoItem.class)
public interface PedidoItemDao extends SqlObject {

    @SqlQuery("SELECT * FROM items_pedido WHERE pedido_id = :pedidoId")
    List<PedidoItem> getByPedido(@Bind("pedidoId") int pedidoId);

    @SqlUpdate("INSERT INTO items_pedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES (:pedidoId, :productoId, :cantidad, :precioUnitario)")
    int add(@Bind("pedidoId") int pedidoId, @Bind("productoId") int productoId, @Bind("cantidad") int cantidad, @Bind("precioUnitario") BigDecimal precioUnitario);

    @SqlUpdate("DELETE FROM items_pedido WHERE pedido_id = :pedidoId")
    int deleteByPedido(@Bind("pedidoId") int pedidoId);
}
