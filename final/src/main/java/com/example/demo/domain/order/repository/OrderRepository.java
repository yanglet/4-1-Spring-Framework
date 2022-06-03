package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 엔티티 명은 order 이지만
 * 테이블 명은 orders 주의
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    public void save(Order order){
        jdbcTemplate.update("insert into orders(member_id, total_price, create_time)" +
                "values (?, ?, ?)",
                order.getMember().getMember_id(),
                order.getTotalPrice(),
                order.getCreateTime());
    }

    public List<Order> findAllPerDay(int day){
        return jdbcTemplate.query("select * from orders where datediff(now(), create_time) < " + day, orderMapper);
    }

    public List<Order> findAllPerWeek(int week){
        return jdbcTemplate.query("select * from orders where datediff(now(), create_time) < " + week * 7, orderMapper);
    }

    public List<Order> findAllPerMonth(int month){
        return jdbcTemplate.query("select * from orders where datediff(now(), create_time) < " + month * 31, orderMapper);
    }

    public void update(Order order){
        jdbcTemplate.update("update orders set total_price = ?" +
                "  where order_id = ?",
                order.getTotalPrice(),
                order.getOrder_id());
    }

    public List<Order> findAll(){
        return jdbcTemplate.query("select * from orders", orderMapper);
    }

    public Order findById(Long order_id){
        return jdbcTemplate.query("select * from orders where order_id = ?",
                orderMapper, order_id).get(0);
    }

    public Order findByCreateTime(LocalDateTime create_time){
        return jdbcTemplate.query("select * from orders where create_time = ?",
                orderMapper, create_time).get(0);
    }
}
