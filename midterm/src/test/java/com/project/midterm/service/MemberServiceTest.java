package com.project.midterm.service;

import com.project.midterm.entity.Member;
import com.project.midterm.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws IOException, ParseException {
        Member member = Member.builder()
                .email("joinTest@naver.com")
                .password("joinPW")
                .name("joinTest")
                .role("user")
                .build();

        memberService.join(member);

        memberRepository.findAllPrint();
    }

    @Test
    public void 회원가입_중복실패() throws IOException, ParseException {
        Member member = Member.builder()
                .email("joinTest@naver.com")
                .password("joinPW")
                .name("joinTest")
                .role("user")
                .build();

        memberService.join(member);

        int size = memberRepository.findAll().size();

        Assertions.assertThat(size).isEqualTo(4);
    }

    @Test
    public void 로그인_성공() throws IOException, ParseException {
        Long loginResult = memberService.login("joinTest@naver.com", "joinPW");

        Assertions.assertThat(loginResult).isEqualTo(1L);
    }

    @Test
    public void 로그인_실패() throws IOException, ParseException {
        Long loginResult = memberService.login("joiasfdTest@naver.com", "joinPW");

        Assertions.assertThat(loginResult).isEqualTo(-1L);
    }
}