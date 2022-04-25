package com.project.midterm.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    // 엔티티 pk
    private Long id;
    // 주문한 고객 정보
    private Member member;
    // 주문한 상품 정보
    private Item item;
    // 주문 총 가격
    private Long totalPrice;
    // 주문시 날짜 및 시간
    private LocalDateTime createDate;

    public Order(Long id) {
        this.id = id;
    }

    @Builder
    public static Order of(Long id, Member member, Item item, Long quantity){
        return new Order(id, member, item, item.getPrice() * quantity, LocalDateTime.now());
    }
}
