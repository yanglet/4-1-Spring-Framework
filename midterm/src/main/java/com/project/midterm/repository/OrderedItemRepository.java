package com.project.midterm.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.midterm.entity.OrderedItem;
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

@Repository
public class OrderedItemRepository {

    public void save(OrderedItem orderedItem) throws IOException, ParseException {
        List<OrderedItem> orderedItemList = findAll();

        setId(orderedItem, orderedItemList);

        // orderedItemList 에 새로 추가할 orderedItem 의 아이템이 존재하면 수량만 추가해주고 save는 하지않음
        for(OrderedItem oi : orderedItemList){
            if( oi.getItem().getName().equals(orderedItem.getItem().getName()) ){
                oi.getItem().setQuantity(oi.getItem().getQuantity() + orderedItem.getItem().getQuantity());
                return;
            }
        }

        orderedItemList.add(orderedItem);

        writeOrderedItemListToJson(orderedItemList);
    }

    public List<OrderedItem> findAll() throws IOException, ParseException {
        return readOrderedItemListFromJson();
    }

    private void setId(OrderedItem orderedItem, List<OrderedItem> orderedItemList) {
        Long id = orderedItemList.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).orElse(new OrderedItem(0L)).getId();

        orderedItem.setId(id + 1);
    }

    private void writeOrderedItemListToJson(List<OrderedItem> orderedItemList) throws IOException {
        // id 넣을때 겹치지 않으려면 id 순으로 정렬되어 있어야함
        Collections.sort(orderedItemList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        FileWriter fileWriter = new FileWriter(DataUtil.ORDERED_ITEM_DATA_PATH);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(orderedItemList, fileWriter);
        fileWriter.close();
    }

    private List<OrderedItem> readOrderedItemListFromJson() throws IOException, ParseException {
        FileReader fileReader = new FileReader(DataUtil.ORDERED_ITEM_DATA_PATH);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);
        JSONArray jsonList = (JSONArray) obj;
        List<OrderedItem> orderedItemList = new ArrayList<>();
        jsonList.stream().forEach(o -> orderedItemList.add(new Gson().fromJson(o.toString(), OrderedItem.class)));
        return orderedItemList;
    }
}
