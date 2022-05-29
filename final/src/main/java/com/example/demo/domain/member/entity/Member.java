package com.example.demo.domain.member.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    private Long member_id;
    private String name;
    private String email;
    private String password;
    private String role; // ROLE_USER, ROLE_ADMIN

    @Builder
    public Member(Long member_id, String name, String email, String password, String role) {
        this.member_id = member_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
