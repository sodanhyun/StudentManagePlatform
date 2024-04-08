package com.codehows.smp.controller;

import com.codehows.smp.constant.Role;
import com.codehows.smp.dto.MemberDto;
import com.codehows.smp.dto.MemberFormDto;
import com.codehows.smp.entity.Member;
import com.codehows.smp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping(value = "/login")
    public String login() {
        return "pages/member/loginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "exception", required = false) String exception,
                             Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        System.out.println(error);
        System.out.println(exception);
        return "pages/member/loginForm";
    }

    @GetMapping(value = "/new")
    public String userForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "pages/member/userForm";
    }

    @PostMapping(value = "/new")
    public String newMember(MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "pages/member/loginForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "pages/member/userForm";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/roles")
    public String roleManage(Model model) {
        List<MemberDto> memberDtoList = memberService.getMembers();
        model.addAttribute("members", memberDtoList);
        model.addAttribute("ADMIN", Role.ADMIN);
        model.addAttribute("USER", Role.USER);
        return "pages/admin/roleManage";
    }

    @ResponseBody
    @PatchMapping(value = "roles")
    public ResponseEntity roleUpdate(@RequestBody MemberDto[] memberDtos) {
        HashMap<String, Object> map = new HashMap<>();
        memberService.updateRoles(memberDtos);
        map.put("result", "ok");
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}
