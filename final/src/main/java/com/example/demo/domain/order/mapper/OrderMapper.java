package com.example.demo.domain.order.mapper;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderMapper implements RowMapper<Order> {
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public OrderMapper(OrderItemRepository orderItemRepository, MemberRepository memberRepository) {
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .order_id(rs.getLong("order_id"))
                .member(memberRepository.findById(rs.getLong("member_id")))
                .orderItemList(orderItemRepository.findByOrderId(rs.getLong("order_id")))
                .totalPrice(rs.getInt("total_price"))
                .createTime(rs.getTimestamp("create_time").toLocalDateTime())
                .build();
    }
}
