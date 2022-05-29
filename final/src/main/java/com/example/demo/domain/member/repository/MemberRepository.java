package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Member member){
        jdbcTemplate.update("insert into member(name, email, password, role)" +
                "values (?, ?, ?, ?)",
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getRole());
    }

    public List<Member> findAll(){
        return jdbcTemplate.query("select * from member",
                new MemberMapper());
    }

    public Member findByEmail(String email){
        return jdbcTemplate.query("select * from member where email = ?",
                new MemberMapper(), email).stream().findFirst().orElse(null);
    }

    public Member findById(Long id){
        return jdbcTemplate.query("select * from member where member_id = ?",
                new MemberMapper(), id).get(0);
    }
}
