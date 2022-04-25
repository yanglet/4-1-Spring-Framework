package com.project.midterm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.midterm.entity.Item;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class fileTest {

    /**
     * test 데이터
     *
     * [
     *   {
     *     "code": "item1",
     *     "name": "itemName1",
     *     "price": 20000,
     *     "quantity": 20
     *   },
     *   {
     *     "code": "item2",
     *     "name": "itemName2",
     *     "price": 15000,
     *     "quantity": 10
     *   }
     * ]
     */

    @Test
    public void 로컬에서파일읽기() throws IOException, ParseException {
        FileReader fileReader = new FileReader("c:/data/localdata.json");
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);

        JSONArray jsonList = (JSONArray) obj;

        List<Item> itemList = new ArrayList<>();

        jsonList.stream().forEach(o -> itemList.add(new Gson().fromJson(o.toString(), Item.class)));

        jsonList.stream().forEach(o -> System.out.println("o = " + o));
    }


    @Test
    public void 로컬에서파일수정() throws IOException, ParseException {
        FileReader fileReader = new FileReader("c:/data/localdata.json");
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);

        JSONArray jsonList = (JSONArray) obj;

        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item(1L, "item1", "itemName1", 20000L, 20L);
        Item item2 = new Item(2L, "item2", "itemName2", 15000L, 10L);

        jsonList.stream().forEach(o -> itemList.add(new Gson().fromJson(o.toString(), Item.class)));

        itemList.add(item1);
        itemList.add(item2);

        FileWriter fileWriter = new FileWriter("c:/data/localdata.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(itemList, fileWriter);
        fileWriter.close();
    }

    @Test
    public void 프로젝트내에서파일읽기() throws IOException, ParseException {
        ClassPathResource resource = new ClassPathResource("data/readtest.json");
        Path path = Paths.get(resource.getURI());
        JSONArray jsonList = (JSONArray) new JSONParser().parse(new FileReader(path.toString()));

        List<Item> itemList = new ArrayList<>();

        jsonList.stream().forEach(o -> itemList.add(new Gson().fromJson(o.toString(), Item.class)));

        Assertions.assertThat(itemList.get(0).getPrice()).isEqualTo(20000);
        Assertions.assertThat(itemList.get(1).getPrice()).isEqualTo(15000);
    }

    @Test
    public void 프로젝트내에서파일쓰기() throws IOException, JSONException {
        ClassPathResource resource = new ClassPathResource("data/writetest.json");
        Path path = Paths.get(resource.getURI());
        File jsonFile = new File(path.toString());

        Item item1 = new Item(1L, "item1", "itemName1", 20000L, 20L);
        Item item2 = new Item(2L, "item2", "itemName2", 15000L, 10L);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("item1", item1.getCode());
        jsonObject.put("item2", item2.getCode());

        writeStringToFile(jsonObject.toString(), jsonFile);
    }

    public static void writeStringToFile(String str, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.close();
    }
}