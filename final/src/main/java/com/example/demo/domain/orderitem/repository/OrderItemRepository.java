package com.example.demo.domain.orderitem.repository;

import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.orderitem.entity.OrderItem;
import com.example.demo.domain.orderitem.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OrderItemMapper orderItemMapper;

    public void save(OrderItem orderItem){
        jdbcTemplate.update("insert into orderitem(order_id, item_id, total_price, quantity)" +
                "values (?, ?, ?, ?)",
                orderItem.getOrder().getOrder_id(),
                orderItem.getItem().getItem_id(),
                orderItem.getTotalPrice(),
                orderItem.getQuantity());
    }

    public List<OrderItem> findAll(){
        return jdbcTemplate.query("select * from orderitem", orderItemMapper);
    }

    public OrderItem findById(Long id){
        return jdbcTemplate.query("select * from orderitem where orderitem_id = ?",
                orderItemMapper, id).get(0);
    }

    public List<OrderItem> findByOrderId(Long order_id){
        return jdbcTemplate.query("select * from orderitem where order_id = ?",
                orderItemMapper, order_id);
    }
}
