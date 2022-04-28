package com.project.midterm.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.midterm.entity.Item;
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

/**
 * 상품 추가
 * 상품 삭제
 * 상품 수정
 * 상품 목록 확인
 */

@Repository
public class ItemRepository {

    public void save(Item item) throws IOException, ParseException {
        List<Item> itemList = readItemListFromJson();

        setId(item, itemList);

        itemList.add(item);

        writeItemListToJson(itemList);
    }

    public void delete(String code) throws IOException, ParseException {
        List<Item> itemList = readItemListFromJson();

        Item findItem = itemList
                .stream()
                .filter(i -> i.getCode().equals(code))
                .findAny()
                .orElseThrow();

        itemList.remove(findItem);

        writeItemListToJson(itemList);
    }

    public void update(Item item) throws IOException, ParseException {
        List<Item> itemList = readItemListFromJson();

        Item findItem = itemList
                .stream()
                .filter(i -> i.getCode().equals(item.getCode()))
                .findAny()
                .orElseThrow();

        item.setId(findItem.getId());

        itemList.remove(findItem);

        itemList.add(item);

        writeItemListToJson(itemList);
    }

    public Item findByName(String name) throws IOException, ParseException {
        List<Item> itemList = findAll();

        return itemList
                .stream()
                .filter(i -> i.getName().equals(name))
                .findAny()
                .orElseThrow();
    }

    public Item findByCode(String code) throws IOException, ParseException {
        List<Item> itemList = findAll();

        return itemList
                .stream()
                .filter(i -> i.getCode().equals(code))
                .findAny()
                .orElseThrow();
    }

    public List<Item> findAll() throws IOException, ParseException {
        return readItemListFromJson();
    }

    public void findAllPrint() throws IOException, ParseException {
        List<Item> itemList = readItemListFromJson();

        itemList.stream().forEach(i -> System.out.println("상품 이름 : " + i.getName()
                        + ", 상품 금액 : " + i.getPrice() + "원"
                        + ", 상품 수량 : " + i.getQuantity()));
        System.out.println();
    }

    private void setId(Item item, List<Item> itemList) {
        Long id = itemList.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).orElse(new Item(0L)).getId();

        item.setId(id + 1);
    }

    private void writeItemListToJson(List<Item> itemList) throws IOException {
        // id 넣을때 겹치지 않으려면 id 순으로 정렬되어 있어야함
        Collections.sort(itemList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        FileWriter fileWriter = new FileWriter(DataUtil.ITEM_DATA_PATH);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(itemList, fileWriter);
        fileWriter.close();
    }

    private List<Item> readItemListFromJson() throws IOException, ParseException {
        FileReader fileReader = new FileReader(DataUtil.ITEM_DATA_PATH);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);
        JSONArray jsonList = (JSONArray) obj;
        List<Item> itemList = new ArrayList<>();
        jsonList.stream().forEach(o -> itemList.add(new Gson().fromJson(o.toString(), Item.class)));
        return itemList;
    }
}
