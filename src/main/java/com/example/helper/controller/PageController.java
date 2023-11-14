package com.example.helper.controller;


import com.example.helper.service.speech.RecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PageController {
    private static final String UPLOADED_FOLDER = "uploads/";
    private final RecognitionService recognitionService;

    @RequestMapping("/main")
    public String mainPage(){
        return "/main";
    }
    @RequestMapping("/speech")
    public String speechPage(){
        return "/speech";
    }
    @RequestMapping("/text")
    public String testPage(){
        return "text";
    }


}
