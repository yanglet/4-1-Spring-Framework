package com.example.demo.domain.order.service;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.orderitem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public void sale(Member member, List<OrderItem> orderItemList){
        int orderTotalPrice = 0;

        /**
         * 뷰에서 개수에 관해서 오류가 안나게 잘 해야함
         */

        for(OrderItem oi : orderItemList){
            orderTotalPrice += oi.getTotalPrice();
            Item findItem = itemRepository.findByCode(oi.getItem().getCode());
            findItem.setQuantity(findItem.getQuantity() - oi.getQuantity());
            itemRepository.updateQuantity(findItem);
        }

        Order order = Order.builder()
                .member(member)
                .orderItemList(orderItemList)
                .totalPrice(orderTotalPrice)
                .createTime(LocalDateTime.now())
                .build();

        orderRepository.save(order);
    }
}
