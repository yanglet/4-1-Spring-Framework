package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save(){
        Member member = Member.builder()
                .name("testname1")
                .email("testemail1")
                .password("testpassword")
                .role("ROLE_USER")
                .build();

//        memberRepository.save(member);
    }

    @Test
    public void findAll(){
        List<Member> memberList = memberRepository.findAll();
        Assertions.assertThat(memberList.size()).isEqualTo(1);
    }
}