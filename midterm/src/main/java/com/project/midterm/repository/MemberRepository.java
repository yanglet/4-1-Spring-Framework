package com.project.midterm.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.midterm.entity.Member;
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
 * 회원 추가
 * 회원 수정
 * 회원 삭제
 * 회원 목록 확인
 */

@Repository
public class MemberRepository {

    public void save(Member member) throws IOException, ParseException {
        List<Member> memberList = findAll();

        setId(member, memberList);

        memberList.add(member);

        writeMemberListToJson(memberList);
    }

    public void delete(String email) throws IOException, ParseException {
        List<Member> memberList = findAll();

        Member findMember = findByEmail(email);

        memberList.remove(findMember);

        writeMemberListToJson(memberList);
    }

    public void update(Member member) throws IOException, ParseException {
        List<Member> memberList = findAll();

        Member findMember = findByEmail(member.getEmail());

        member.setId(findMember.getId());

        memberList.remove(findMember);

        memberList.add(member);

        writeMemberListToJson(memberList);
    }

    /**
     * 못 찾을 경우 null을 반환함 주의할 것 !@!@!@!@!@!@!@!
     */
    public Member findByEmail(String email) throws IOException, ParseException {
        List<Member> memberList = findAll();

        return memberList
                .stream()
                .filter(m -> m.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }

    public List<Member> findAll() throws IOException, ParseException {
        return readMemberListFromJson();
    }

    public void findAllPrint() throws IOException, ParseException {
        List<Member> memberList = findAll();

        System.out.println("총 회원수 : " + memberList.size() + "명");
        memberList.stream().forEach(m -> System.out.println("회원 이름 : " + m.getName()));
    }

    private void setId(Member member, List<Member> memberList) {
        Long id = memberList.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).orElse(new Member(0L)).getId();

        member.setId(id + 1);
    }

    private void writeMemberListToJson(List<Member> memberList) throws IOException {
        // id 넣을때 겹치지 않으려면 id 순으로 정렬되어 있어야함
        Collections.sort(memberList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        FileWriter fileWriter = new FileWriter(DataUtil.MEMBER_DATA_PATH);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(memberList, fileWriter);
        fileWriter.close();
    }

    private List<Member> readMemberListFromJson() throws IOException, ParseException {
        FileReader fileReader = new FileReader(DataUtil.MEMBER_DATA_PATH);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(fileReader);
        JSONArray jsonList = (JSONArray) obj;
        List<Member> memberList = new ArrayList<>();
        jsonList.stream().forEach(o -> memberList.add(new Gson().fromJson(o.toString(), Member.class)));
        return memberList;
    }
}
