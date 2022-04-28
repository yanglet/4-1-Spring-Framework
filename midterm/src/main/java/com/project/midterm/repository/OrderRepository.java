package com.project.midterm.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.midterm.entity.Item;
import com.project.midterm.entity.Member;
import com.project.midterm.entity.Order;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 추가
 */

@Repository
public class OrderRepository {

    public void save(Order order) throws IOException, ParseException {
        List<Order> orderList = findAll();

        setId(order, orderList);

        orderList.add(order);

        writeOrderListToJson(orderList);
    }

    public List<Order> findAll() throws IOException, ParseException {
        return readOrderListFromJson();
    }

    public void findAllByMemberPrint(Member member) throws IOException, ParseException {
        List<Order> orderList = findAll();

        List<Order> findOrderList = orderList
                .stream()
                .filter(o -> o.getMember().getEmail().equals(member.getEmail()))
                .collect(Collectors.toList());

        findOrderList.stream().forEach(o -> System.out.println("주문 상품 이름 : " + o.getItem().getName()
                + ", 주문 상품 수량 : " + o.getItem().getQuantity() + "개"
                + ", 주문 상품 금액 : " + o.getItem().getPrice() + "원"
                + ", 주문 시간 : " + o.getCreateDate()));
        System.out.println();
    }

    private void setId(Order order, List<Order> orderList) {
        Long id = orderList.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).orElse(new Order(0L)).getId();

        order.setId(id + 1);
    }

    private void writeOrderListToJson(List<Order> orderList) throws IOException {
        // id 넣을때 겹치지 않으려면 id 순으로 정렬되어 있어야함
        Collections.sort(orderList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        FileWriter fileWriter = new FileWriter(DataUtil.ORDER_DATA_PATH);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(orderList, fileWriter);
        fileWriter.close();
    }

    private List<Order> readOrderListFromJson() throws IOException, ParseException {
        FileReader fileReader = new FileReader(DataUtil.ORDER_DATA_PATH);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);
        JSONArray jsonList = (JSONArray) obj;
        List<Order> orderList = new ArrayList<>();
        jsonList.stream().forEach(o -> orderList.add(new Gson().fromJson(o.toString(), Order.class)));
        return orderList;
    }
}
