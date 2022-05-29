package com.example.demo.domain.orderitem.repository;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.orderitem.entity.OrderItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderItemRepositoryTest {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save(){
        Order findOrder = orderRepository.findById(1L);
        Item findItem = itemRepository.findById(1L);

        OrderItem orderItem = OrderItem.builder()
                .item(findItem)
                .order(findOrder)
                .totalPrice(100000)
                .quantity(10)
                .build();

//        orderItemRepository.save(orderItem);
    }

    @Test
    public void findAll(){
        List<OrderItem> list = orderItemRepository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(1);
    }
}