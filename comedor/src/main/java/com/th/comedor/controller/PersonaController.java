package com.th.comedor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonaController {
    
    @GetMapping(value = "/personas")
    public String listarPersona(){

        return "persona/personas";
    }
}
