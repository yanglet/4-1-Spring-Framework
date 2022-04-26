package com.project.midterm.service;

import com.project.midterm.repository.OrderedItemRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class AnalysisServiceTest {

    @Autowired
    private OrderedItemRepository orderedItemRepository;
    @Autowired
    private AnalysisService analysisService;

    @Test
    public void 하루판매량() throws IOException, ParseException {
        analysisService.printPerDay();
    }

    @Test
    public void 일주일판매량() throws IOException, ParseException {
        analysisService.printPerWeek();
    }

    @Test
    public void 한달판매량() throws IOException, ParseException {
        analysisService.printPerMonth(0);
    }

    @Test
    public void 매출액() throws IOException, ParseException {
        analysisService.printSales();
    }

    @Test
    public void 최다판매상품() throws IOException, ParseException {
        analysisService.printTopItem();
    }

}