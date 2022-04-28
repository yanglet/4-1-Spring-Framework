package com.project.midterm.service;

import com.project.midterm.entity.Member;
import com.project.midterm.repository.MemberRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 회원가입
 * 로그인
 */

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long login(String email, String password) throws IOException, ParseException {
        Member findMember = memberRepository.findByEmail(email);

        if(findMember == null){
            return -1L;
        }
        if(findMember.getPassword().equals(password)){
            // 로그인 성공
            return 1L;
        }
        // 로그인 실패
        return -1L;
    }

    public void join(Member member) throws IOException, ParseException {
        if( !isValidateDuplicateMember(member) ){
            member.setRole("user");
            memberRepository.save(member);
        }else{
            System.out.println("사용중인 이메일입니다.\n다시 입력해주세요.\n");
        }
    }

    private boolean isValidateDuplicateMember(Member member) throws IOException, ParseException {
        if( memberRepository.findByEmail(member.getEmail()) != null ){
            return true; // 중복
        }
        return false; // 중복아님
    }
}
