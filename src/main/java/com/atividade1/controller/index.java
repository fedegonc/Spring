package com.atividade1.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class index {

    @RequestMapping("/")
    public String inicio() {
        // Lógica para cargar la página de inicio
        return "redirect:/inicio"; // Nombre de la vista que se mostrará en la página de inicio
    }
}
