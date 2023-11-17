package com.example.helper.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private static final String UPLOADED_FOLDER = "uploads/";

    @RequestMapping("/main")
    public String mainPage(){
        return "/main";
    }
    @RequestMapping("/speech")
    public String speechPage(){
        return "/speech";
    }
    @RequestMapping("/script")
    public String testPage(){
        return "/script";
    }


}
