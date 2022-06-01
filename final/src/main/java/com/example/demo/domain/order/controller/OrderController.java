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
import org.springframework.boot.Banner;
import org.springframework.security.access.annotation.Secured;
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

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/order")
    public String orderForm(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "basic/order";
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/order")
    public String order(@RequestParam("item_id1") Long item_id1,
                        @RequestParam("quantity1") Integer quantity1,
                        @RequestParam(value = "item_id2", required = false) Long item_id2,
                        @RequestParam(value = "quantity2", required = false) Integer quantity2,
                        Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(userDetails.getUsername());

        Item item1 = itemRepository.findById(item_id1);
        Item item2 = null;

        if(item_id2 != null){
            item2 = itemRepository.findById(item_id2);
        }

        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem orderItem1 = OrderItem.builder()
                .item(item1)
                .totalPrice(item1.getPrice() * quantity1)
                .quantity(quantity1)
                .build();

        orderItemList.add(orderItem1);

        if(item2 != null){
            OrderItem orderItem2 = OrderItem.builder()
                    .item(item2)
                    .totalPrice(item2.getPrice() * quantity2)
                    .quantity(quantity2)
                    .build();

            orderItemList.add(orderItem2);
        }

        orderService.sale(member, orderItemList);

        return "redirect:/orders";
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "basic/orders";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/analysis")
    public String analysis(Model model){
        model.addAttribute("salesPerDay", orderService.salesPerDay());
        model.addAttribute("bestSeller", orderService.bestSeller());
        return "basic/analysis";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/analysis/day")
    public String orderPerDay(Model model){
        model.addAttribute("orders", orderRepository.findAllPerDay(1));
        return "basic/day";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/analysis/week")
    public String orderPerWeek(Model model){
        model.addAttribute("orders", orderRepository.findAllPerWeek(1));
        return "basic/week";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/analysis/month")
    public String orderPerMonth(Model model){
        model.addAttribute("orders", orderRepository.findAllPerMonth(1));
        return "basic/month";
    }
}
