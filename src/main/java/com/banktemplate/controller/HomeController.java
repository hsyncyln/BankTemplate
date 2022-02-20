package com.banktemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //Home Page
    //http://localhost:8090
    @GetMapping("")
    public String getHome(Model model){
        return "/home/index";
    }




}
