package com.example.demo.domain.order.repository;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save(){
        Member findMember = memberRepository.findByEmail("testemail1");

        Order order = Order.builder()
                .member(findMember)
                .totalPrice(100000)
                .createTime(LocalDateTime.now())
                .build();

//        orderRepository.save(order);
    }

    @Test
    public void findAll(){
        List<Order> orderList = orderRepository.findAll();
        System.out.println("orderitem 개수 = " + orderList.get(0).getOrderItemList().get(0).getQuantity());
        Assertions.assertThat(orderList.size()).isEqualTo(1);
    }
}