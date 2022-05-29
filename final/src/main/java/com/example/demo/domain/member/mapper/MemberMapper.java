package com.example.demo.domain.member.mapper;

import com.example.demo.domain.member.entity.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MemberMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Member.builder()
                .member_id(rs.getLong("member_id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
                .build();
    }
}
