package com.codehows.smp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {
    private String path = "/errors/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            int statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return path + "401";
            }
            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return path + "403";
            }
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return path + "404";
            }
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return path + "500";
            }
        }
        return "error";
    }

}
