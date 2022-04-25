package com.project.midterm.repository;

import com.project.midterm.entity.Item;
import com.project.midterm.entity.Member;
import com.project.midterm.entity.Order;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() throws IOException, ParseException {
        Item item = new Item(2L, "item2", "itemName2", 15000L, 10L);
        Member findMember = memberRepository.findByEmail("test1@naver.com");

        Order order = Order.builder()
                .item(item)
                .member(findMember)
                .quantity(2L)
                .build();

//        orderRepository.save(order);

        orderRepository.findAllByMemberPrint(findMember);
    }
}