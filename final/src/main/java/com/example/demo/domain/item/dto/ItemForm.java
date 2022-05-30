package com.example.demo.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemForm {
    @NotEmpty(message = "필수 입력란입니다.")
    private String code;
    @NotEmpty(message = "필수 입력란입니다.")
    private String name;
    @NotNull(message = "필수 입력란입니다.")
    @Min(value = 500, message = "500원 이상의 가격을 입력해주세요.")
    private int price;
    @NotNull(message = "필수 입력란입니다.")
    @Min(value = 1, message = "1이상의 수량을 입력주세요.")
    private int quantity;
}
