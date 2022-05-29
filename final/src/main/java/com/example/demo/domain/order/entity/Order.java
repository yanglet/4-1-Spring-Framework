package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.orderitem.entity.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * order 가 예약어이므로
 * 테이블 이름은 orders 로 해야함
 * 쿼리날릴때 주의하자 !!
 */
public class Order {
    private Long order_id;
    private Member member; // 판매 직원
    private List<OrderItem> orderItemList = new ArrayList<>();
    private int totalPrice; // 주문된 상품들의 총 가격
    private LocalDateTime createTime;

    @Builder
    public Order(Long order_id, Member member, List<OrderItem> orderItemList, int totalPrice, LocalDateTime createTime) {
        this.order_id = order_id;
        this.member = member;
        this.orderItemList = orderItemList;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
    }
}
