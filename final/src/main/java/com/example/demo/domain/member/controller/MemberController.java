package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.MemberForm;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public String main(){
        return "basic/main";
    }

    @GetMapping("/main")
    public String mainForm(){
        return "basic/main";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "basic/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "basic/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("memberForm") MemberForm memberForm,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "basic/join";
        }else{
            Member member = Member.builder()
                    .name(memberForm.getName())
                    .email(memberForm.getEmail())
                    .password(memberForm.getPassword())
                    .role(memberForm.getRole())
                    .build();

            memberService.join(member);

            return "redirect:/loginForm";
        }
    }
}
