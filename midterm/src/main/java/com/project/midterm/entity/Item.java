package com.project.midterm.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    // 엔티티 pk
    private Long id;
    // 상품 코드
    private String code;
    // 상품 이름
    private String name;
    // 상품 가격
    private Long price;
    // 상품 수량
    private Long quantity;

    @Builder
    public Item(Long id, String code, String name, Long price, Long quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(Long id) {
        this.id = id;
    }
}
