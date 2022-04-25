package com.project.midterm.repository;

import com.project.midterm.entity.Member;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() throws IOException, ParseException {
        Member member = Member.builder()
                .email("yanglet@naver.com")
                .name("yanglet")
                .password("password")
                .role("manager")
                .build();

        memberRepository.save(member);

        memberRepository.findAllPrint();
    }

    @Test
    public void delete() throws IOException, ParseException {
        memberRepository.delete("yanglet@naver.com");

        memberRepository.findAllPrint();
    }

    @Test
    public void test() throws IOException, ParseException {
        Member member = Member.builder()
                .email("yanglet@naver.com")
                .name("양글렛")
                .password("패스워드")
                .role("user")
                .build();

        memberRepository.update(member);

        memberRepository.findAllPrint();
    }

}
