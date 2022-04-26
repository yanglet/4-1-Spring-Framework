package com.project.midterm.service;

import com.project.midterm.entity.Item;
import com.project.midterm.entity.Member;
import com.project.midterm.entity.Order;
import com.project.midterm.entity.OrderedItem;
import com.project.midterm.repository.OrderRepository;
import com.project.midterm.repository.OrderedItemRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 판매 (주문 처리)
 */

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final OrderedItemRepository orderedItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemService itemService, OrderedItemRepository orderedItemRepository) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.orderedItemRepository = orderedItemRepository;
    }

    public Long sale(Item item, Member member, Long quantity, Long money) throws IOException, ParseException {
        Long change = money - item.getPrice() * quantity;
        Long remainingStock = item.getQuantity() - quantity;

        if(remainingStock <= 0){
            System.out.println("상품의 수량이 부족합니다.\n상품 수량 : " + item.getQuantity() + "개");
            return -1L;
        }

        if( change >= 0L ){
            itemService.removeStock(item, quantity);

            item.setQuantity(quantity); // 주문 내역 상에는 주문한 만큼의 수량으로 표시되어야 함

            Order order = Order.builder()
                    .member(member)
                    .item(item)
                    .quantity(quantity)
                    .build();

            orderedItemRepository.save(OrderedItem.builder()
                    .item(item)
                    .totalPrice(order.getTotalPrice())
                    .createTime(order.getCreateDate())
                    .build());
            
            orderRepository.save(order);

            System.out.println("거스름 돈 : " + change + "원");

            return change;
        }else{
            System.out.println("금액이 부족합니다.");
            return -1L;
        }
    }
}
