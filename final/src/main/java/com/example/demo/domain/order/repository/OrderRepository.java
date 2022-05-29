package com.example.demo.domain.order.repository;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public List<Order> findAll(){
        return jdbcTemplate.query("select * from orders", orderMapper);
    }

    public Order findById(Long id){
        return jdbcTemplate.query("select * from orders where order_id = ?",
                orderMapper, id).get(0);
    }
}
