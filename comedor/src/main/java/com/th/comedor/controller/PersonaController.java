package com.th.comedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.th.comedor.model.serviceI.IPersonaService;
@Controller
public class PersonaController {
    
    @Autowired
    private IPersonaService personaService;


    @GetMapping(value = "/personas")
    public String listarPersona(Model model){
        model.addAttribute("personas", personaService.findAll());
        return "persona/personas";
    }
}
