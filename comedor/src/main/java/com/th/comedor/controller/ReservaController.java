package com.th.comedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.th.comedor.model.serviceI.IPersonaService;


@Controller
public class ReservaController {
    
    @Autowired
    private IPersonaService personaService;

    @GetMapping("/buscador")
    public String buscador() {
        return "reserva/buscador";
    }
    
    @GetMapping("buscador/persona/{texto}")
    public String buscarArchivos(@PathVariable(value="texto", required = false) String ci, ModelMap model){
    	System.out.println("des="+ci);
    	model.addAttribute("persona", personaService.listaPersonasPorCi(ci.toUpperCase()));
    	
		return "content :: content1";
    }
}
