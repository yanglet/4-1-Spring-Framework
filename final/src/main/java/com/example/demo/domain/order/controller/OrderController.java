package com.example.demo.domain.order.controller;

import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.repository.ItemRepository;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.order.service.OrderService;
import com.example.demo.domain.orderitem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/order")
    public String orderForm(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "basic/order";
    }

    @PostMapping("/order")
    public String order(@RequestParam("item_id1") Long item_id1,
                        @RequestParam("quantity1") int quantity1,
                        @RequestParam("item_id2") Long item_id2,
                        @RequestParam("quantity2") int quantity2,
                        Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(userDetails.getUsername());

        Item item1 = itemRepository.findById(item_id1);
        Item item2 = itemRepository.findById(item_id2);

        OrderItem orderItem1 = OrderItem.builder()
                .item(item1)
                .totalPrice(item1.getPrice() * quantity1)
                .quantity(quantity1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .item(item2)
                .totalPrice(item2.getPrice() * quantity2)
                .quantity(quantity2)
                .build();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        orderService.sale(member, orderItemList);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "basic/orders";
    }
}
