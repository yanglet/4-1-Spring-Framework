package com.example.demo.domain.item.repository;

import com.example.demo.domain.item.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save(){
        Item item = Item.builder()
                .code("testcode1")
                .name("testname1")
                .price(10000)
                .quantity(100)
                .createTime(LocalDateTime.now())
                .build();

//        itemRepository.save(item);
    }

    @Test
    public void findAll(){
        List<Item> itemList = itemRepository.findAll();
        Assertions.assertThat(itemList.size()).isEqualTo(1);
    }
}