package com.example.demo.domain.item.controller;

import com.example.demo.domain.item.dto.ItemForm;
import com.example.demo.domain.item.entity.Item;
import com.example.demo.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping("/items")
    public String items(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "basic/items";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/items/new")
    public String newItemForm(Model model){
        model.addAttribute("itemForm", new ItemForm());
        return "basic/createItemForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/items/new")
    public String createItem(@Validated @ModelAttribute("itemForm") ItemForm itemForm,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "basic/createItemForm";
        }else{
            Item item = Item.builder()
                    .code(itemForm.getCode())
                    .name(itemForm.getName())
                    .price(itemForm.getPrice())
                    .quantity(itemForm.getQuantity())
                    .createTime(LocalDateTime.now())
                    .build();

            itemRepository.save(item);

            return "redirect:/items";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/items/{item_id}/edit")
    public String updateItemForm(@PathVariable("item_id") Long item_id, Model model){
        Item item = itemRepository.findById(item_id);

        ItemForm itemForm = ItemForm.builder()
                .code(item.getCode())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();

        model.addAttribute("itemForm", itemForm);
        return "basic/updateItemForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/items/{item_id}/edit")
    public String updateItem(@Validated @ModelAttribute("itemForm") ItemForm itemForm,
                             BindingResult bindingResult,
                             @PathVariable("item_id") Long item_id){
        if(bindingResult.hasErrors()){
            return "basic/createItemForm";
        }else{
            Item item = Item.builder()
                    .item_id(itemRepository.findByName(itemForm.getName()).getItem_id())
                    .code(itemForm.getCode())
                    .name(itemForm.getName())
                    .price(itemForm.getPrice())
                    .quantity(itemForm.getQuantity())
                    .createTime(LocalDateTime.now())
                    .build();

            itemRepository.update(item);

            return "redirect:/items";
        }
    }
}
