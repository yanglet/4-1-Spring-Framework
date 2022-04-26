package com.project.midterm.main;

import com.project.midterm.config.JavaConfig;
import com.project.midterm.repository.ItemRepository;
import com.project.midterm.repository.MemberRepository;
import com.project.midterm.repository.OrderRepository;
import com.project.midterm.service.AnalysisService;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        MemberRepository memberRepository = (MemberRepository) context.getBean("memberRepository");
        ItemRepository itemRepository = (ItemRepository) context.getBean("itemRepository");
        OrderRepository orderRepository = (OrderRepository) context.getBean("orderRepository");
        AnalysisService analysisService = (AnalysisService) context.getBean("analysisService");

    }
}
