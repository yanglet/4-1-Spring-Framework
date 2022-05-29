package com.example.demo.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {
    @NotEmpty(message = "필수 입력란입니다.")
    private String name;
    @NotNull
    @Size(min = 6, max = 14, message = "비밀번호는 6자리 이상 14자리 이하여야 합니다.")
    private String password;
    @NotNull
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotNull(message = "권한을 선택해주세요.")
    private String role;
}
