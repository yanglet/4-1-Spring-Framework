package com.project.midterm.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    // 엔티티 pk
    private Long id;
    // 회원 이름
    private String name;
    // 로그인 ID
    private String email;
    // 로그인 PW
    private String password;
    // 권한 ( 직원 user , 매니저 manager )
    private String role;

    @Builder
    public Member(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member(Long id) {
        this.id = id;
    }
}
