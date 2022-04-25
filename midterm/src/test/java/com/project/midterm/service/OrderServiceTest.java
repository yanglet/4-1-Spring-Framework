package com.project.midterm.service;

import com.project.midterm.entity.Item;
import com.project.midterm.entity.Member;
import com.project.midterm.repository.ItemRepository;
import com.project.midterm.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void 판매_성공() throws IOException, ParseException {
        Item item1 = itemRepository.findByCode("item1");
        Member findMember = memberRepository.findByEmail("test1@naver.com");

        Long result = orderService.sale(item1, findMember, 2L, 50000L);

        Assertions.assertThat(result).isEqualTo(10000L);
    }

    @Test
    public void 판매_실패_금액부족() throws IOException, ParseException {
        Item item1 = itemRepository.findByCode("item1");
        Member findMember = memberRepository.findByEmail("test1@naver.com");

        Long result = orderService.sale(item1, findMember, 4L, 50000L);

        Assertions.assertThat(result).isEqualTo(-1);
    }

    @Test
    public void 판매_실패_상품수량부족() throws IOException, ParseException {
        Item item1 = itemRepository.findByCode("item1");
        Member findMember = memberRepository.findByEmail("test1@naver.com");

        Long result = orderService.sale(item1, findMember, 40L, 50000L);

        Assertions.assertThat(result).isEqualTo(-1);
    }
}