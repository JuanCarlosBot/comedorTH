package com.th.comedor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/tahuamanu")
    public String home(){

        return "home";
    }
}
