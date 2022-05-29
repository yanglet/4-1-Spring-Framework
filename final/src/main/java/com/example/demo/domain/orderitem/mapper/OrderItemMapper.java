package com.example.demo.domain.orderitem.mapper;

import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.orderitem.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemMapper implements RowMapper<OrderItem> {
    private final ItemRepository itemRepository;

    @Autowired
    public OrderItemMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrderItem.builder()
                .orderitem_id(rs.getLong("orderitem_id"))
                .item(itemRepository.findById(rs.getLong("item_id")))
                .totalPrice(rs.getInt("total_price"))
                .quantity(rs.getInt("quantity"))
                .build();
    }
}
