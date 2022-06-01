package com.example.demo.domain.order.service;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.orderitem.entity.OrderItem;
import com.example.demo.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public void sale(Member member, List<OrderItem> orderItemList){
        int orderTotalPrice = 0;

        /**
         * 뷰에서 개수에 관해서 오류가 안나게 잘 해야함
         */
        Order order = Order.builder()
                .member(member)
                .orderItemList(orderItemList)
                .createTime(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        order = orderRepository.findByCreateTime(order.getCreateTime());

        for(OrderItem oi : orderItemList){
            orderTotalPrice += oi.getTotalPrice();
            Item findItem = itemRepository.findByCode(oi.getItem().getCode());
            findItem.setQuantity(findItem.getQuantity() - oi.getQuantity());
            itemRepository.update(findItem);
            oi.setOrder(order);
            orderItemRepository.save(oi);
        }

        order.setTotalPrice(orderTotalPrice);

        order.getOrderItemList().forEach(o -> System.out.println("o = " + o.getItem().getName()));

        orderRepository.update(order);
    }

    public int salesPerDay(){
        int total_price = 0;

        for(Order o : orderRepository.findAllPerDay(1)){
            total_price += o.getTotalPrice();
        }

        return total_price;
    }

    public String bestSeller(){
        return orderItemRepository.findBestSeller().getItem().getName();
    }
}
