package com.example.demo.domain.item.repository;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Item item){
        jdbcTemplate.update("insert into item(code, name, price, quantity, create_time)" +
                "values (?, ?, ?, ?, ?)",
                item.getCode(),
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                item.getCreateTime());
    }

    public List<Item> findAll(){
        return jdbcTemplate.query("select * from item", new ItemMapper());
    }

    public void update(Item item){
        jdbcTemplate.update("update item set code = ?, name = ?, price = ?, quantity = ?" +
                        "  where item_id = ?",
                item.getCode(),
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                item.getItem_id());
    }

    public Item findByName(String name){
        return jdbcTemplate.query("select * from item where name = ?",
                new ItemMapper(), name).get(0);
    }

    public Item findByCode(String code){
        return jdbcTemplate.query("select * from item where code = ?",
                new ItemMapper(), code).get(0);
    }

    public Item findById(Long id){
        return jdbcTemplate.query("select * from item where item_id = ?",
                new ItemMapper(), id).get(0);
    }
}
