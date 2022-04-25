package com.project.midterm.service;

import com.project.midterm.entity.Item;
import com.project.midterm.repository.ItemRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 재고관리
 * - 더하기
 * - 빼기
 */

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addStock(Item item, Long value) throws IOException, ParseException {
        item.setQuantity(item.getQuantity() + value);
        itemRepository.update(item);
    }

    public void removeStock(Item item, Long value) throws IOException, ParseException {
        item.setQuantity(item.getQuantity() - value);
        itemRepository.update(item);
    }
}
