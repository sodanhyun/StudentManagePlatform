package com.codehows.smp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/team")
@Controller
@RequiredArgsConstructor
public class TeamController {
    @GetMapping(value = "/info")
    public String showInfo() {
        return "pages/teamManage/teamInfo";
    }

    @GetMapping(value = "/hist")
    public String showHist() {
        return "pages/teamManage/teamHist";
    }
}
