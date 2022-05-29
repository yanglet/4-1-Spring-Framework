package com.example.demo.domain.item.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    private Long item_id;
    private String code;
    private String name;
    private int price;
    private int quantity;
    private LocalDateTime createTime; // 입고 날짜

    @Builder
    public Item(Long item_id, String code, String name, int price, int quantity, LocalDateTime createTime) {
        this.item_id = item_id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.createTime = createTime;
    }
}
