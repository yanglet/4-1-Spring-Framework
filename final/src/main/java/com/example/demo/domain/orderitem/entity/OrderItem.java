package com.example.demo.domain.orderitem.entity;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.order.entity.Order;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    private Long orderitem_id;
    private Item item;
    private Order order;
    private int totalPrice; // 상품의 총 가격 ( 상품 가격 * 수량 )
    private int quantity;

    @Builder
    public OrderItem(Long orderitem_id, Item item, Order order, int totalPrice, int quantity) {
        this.orderitem_id = orderitem_id;
        this.item = item;
        this.order = order;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }
}