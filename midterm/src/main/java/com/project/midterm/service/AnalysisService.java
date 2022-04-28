package com.project.midterm.service;

import com.project.midterm.entity.Item;
import com.project.midterm.entity.Order;
import com.project.midterm.repository.OrderRepository;
import com.project.midterm.repository.OrderedItemRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 통계 분석
 * - 하루 판매량
 * - 일주일 판매량
 * - 한달 판매량
 * - 매출액
 * - 최다 판매 상품
 */

@Service
public class AnalysisService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;

    @Autowired
    public AnalysisService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository) {
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
    }

    public void printPerDay() throws IOException, ParseException {
        List<Order> dayList = orderRepository.findAll().
                stream()
                .filter(o -> getPeriod(o.getCreateDate()).getDays() == 0
                        && getPeriod(o.getCreateDate()).getYears() == 0
                        && getPeriod(o.getCreateDate()).getMonths() == 0)
                .collect(Collectors.toList());

        dayList.stream()
                .forEach(o -> System.out.println("구매자 : " + o.getMember().getName()
                        + ", 판매 상품 이름 : " + o.getItem().getName()
                        + ", 판매 수량 : " + o.getItem().getQuantity()));
        System.out.println();
    }

    public void printPerWeek() throws IOException, ParseException {
        List<Order> weekList = orderRepository.findAll().stream()
                .filter(o -> getPeriod(o.getCreateDate()).getDays() <= 6
                        && getPeriod(o.getCreateDate()).getYears() == 0
                        && getPeriod(o.getCreateDate()).getMonths() == 0)
                .collect(Collectors.toList());

        weekList.stream()
                .forEach(o -> System.out.println("구매자 : " + o.getMember().getName()
                        + ", 판매 상품 이름 : " + o.getItem().getName()
                        + ", 판매 수량 : " + o.getItem().getQuantity()));
        System.out.println();
    }

    // month = 1 이면 지난 1달간 판매량
    public void printPerMonth(int month) throws IOException, ParseException {
        List<Order> monthList = orderRepository.findAll().
                stream()
                .filter(o -> getPeriod(o.getCreateDate()).getYears() == 0
                        && getPeriod(o.getCreateDate()).getMonths() <= month)
                .collect(Collectors.toList());

        monthList.stream()
                .forEach(o -> System.out.println("구매자 : " + o.getMember().getName()
                        + ", 판매 상품 이름 : " + o.getItem().getName()
                        + ", 판매 수량 : " + o.getItem().getQuantity()));
        System.out.println();
    }

    // 매출액
    public void printSales() throws IOException, ParseException {
        List<Order> dayList = orderRepository.findAll().
                stream()
                .filter(o -> getPeriod(o.getCreateDate()).getDays() == 0
                        && getPeriod(o.getCreateDate()).getYears() == 0
                        && getPeriod(o.getCreateDate()).getMonths() == 0)
                .collect(Collectors.toList());

        Long sales = 0L;

        for(Order o : dayList){
            sales += o.getTotalPrice();
        }

        System.out.println("매출액 : " + sales + "원\n");
    }

    public void printTopItem() throws IOException, ParseException {
        Item item = orderedItemRepository.findAll()
                .stream()
                .max((o1, o2) -> (int) (o1.getItem().getQuantity() - o2.getItem().getQuantity()))
                .orElseThrow()
                .getItem();

        System.out.println("최다 판매 상품 : " + item.getName() + "\n");
    }

    public Period getPeriod(LocalDateTime createTime){
        return Period.between(LocalDate.from(createTime), LocalDate.now());
    }
}
