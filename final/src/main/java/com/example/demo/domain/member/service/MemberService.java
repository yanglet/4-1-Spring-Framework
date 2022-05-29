package com.example.demo.domain.member.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(Member member){
        if( !isValidateDuplicateMember(member) ){ // 중복 회원이 아닐 경우 회원가입 가능
            // 스프링 시큐리티 로그인을 위해 회원가입시에 비밀번호 인코딩
            String encodePw = bCryptPasswordEncoder.encode(member.getPassword());
            member.setPassword(encodePw);

            memberRepository.save(member);
        }
    }

    private boolean isValidateDuplicateMember(Member member){
        if(memberRepository.findByEmail(member.getEmail()) != null){
            return true;
        }
        return false;
    }
}
