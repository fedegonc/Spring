package com.atividade1.controller;

import com.atividade1.model.Livros;
import com.atividade1.repository.LivrosRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class InicioController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String inicio() {
        return "inicio/home";
    }

}
