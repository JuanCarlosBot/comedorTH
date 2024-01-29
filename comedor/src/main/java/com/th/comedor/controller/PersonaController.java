package com.th.comedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.th.comedor.model.entity.Persona;
import com.th.comedor.model.serviceI.IPersonaService;
import com.th.comedor.model.serviceI.ISeccionService;
@Controller
public class PersonaController {
    
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private ISeccionService seccionService;

    @GetMapping(value = "/personas")
    public String listarPersona(Model model){
        model.addAttribute("persona", new Persona());
        model.addAttribute("secciones", seccionService.findAll());
        model.addAttribute("personas", personaService.findAll());
        return "persona/personas";
    }

    @PostMapping(value = "/guardarPersona")
    public String guardarPersona(@Validated Persona persona, RedirectAttributes redirectAttrs){
        
        persona.setEstado("A");
        personaService.save(persona);

        redirectAttrs
                .addFlashAttribute("mensaje", "Registro Exitoso de la Persona")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/personas";
    }

    @GetMapping(value = "/modificarPersona/{id_persona}")
    public String nodificarPersonaGet(@PathVariable("id_persona") Long id_persona , Model model){
        Persona persona = personaService.findOne(id_persona);
        model.addAttribute("persona", persona);
        model.addAttribute("secciones", seccionService.findAll());
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("edit", "true");
        return "persona/personas";
    }
    @PostMapping(value = "/guardarModificadoPersona")
    public String guardarPersonaModificado(@Validated Persona persona, RedirectAttributes redirectAttrs){
        
        persona.setEstado("A");
        personaService.save(persona);

        redirectAttrs
                .addFlashAttribute("mensaje", "Modificacion exitoso.")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/personas";
    }
}
