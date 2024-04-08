package com.codehows.smp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/schedule")
@Controller
@RequiredArgsConstructor
public class ScheduleController {
    @GetMapping(value = "/info")
    public String showInfo() {
        return "pages/scheduleManage/calendar";
    }
}
