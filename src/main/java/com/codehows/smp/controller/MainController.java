package com.codehows.smp.controller;

import com.codehows.smp.dto.MemberFormDto;
import com.codehows.smp.dto.SessionUser;
import com.codehows.smp.entity.Member;
import com.codehows.smp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession session;
    private final MemberService memberService;

    @GetMapping(value="/")
    public String main(Principal principal, Model model) {
//        String name = "";
//        if(session.getAttribute("user") == null) {
//            name = memberService.getMemberName(principal.getName());
//        }else {
//            SessionUser user = (SessionUser)session.getAttribute("user");
//            name = user.getName();
//        }
//        model.addAttribute("userName", name);
        return "pages/main";
    }

}
