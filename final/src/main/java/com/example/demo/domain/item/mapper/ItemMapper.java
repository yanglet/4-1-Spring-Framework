package com.example.demo.domain.item.mapper;

import com.example.demo.domain.item.entity.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Item.builder()
                .item_id(rs.getLong("item_id"))
                .code(rs.getString("code"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .quantity(rs.getInt("quantity"))
                .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                .build();
    }
}
