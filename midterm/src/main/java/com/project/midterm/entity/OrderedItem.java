package com.project.midterm.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedItem {
    // pk
    private Long id;
    private Item item;
    private Long totalPrice;
    private LocalDateTime createTime;

    public OrderedItem(Long id){
        this.id = id;
    }

    @Builder
    public static OrderedItem of(Long id, Item item, Long totalPrice, LocalDateTime createTime){
        return new OrderedItem(id, item, totalPrice, createTime);
    }
}
