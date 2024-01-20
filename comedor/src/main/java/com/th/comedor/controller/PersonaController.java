package com.th.comedor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonaController {
    
    @GetMapping(value = "/persona")
    public String listarPersona(){

        return "persona/persona";
    }
}
