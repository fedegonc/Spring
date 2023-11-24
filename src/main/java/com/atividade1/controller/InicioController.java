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
    LivrosRepository LivrosRepository;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String inicio() {
        return "home";
    }

    @RequestMapping(value = "/livros/preco/{preco}", method = RequestMethod.GET)
    public ModelAndView getLivroPreco(@PathVariable("preco") double preco) {
        ModelAndView mv = new ModelAndView("/livros/listarlivros");
        List<Livros> livros = LivrosRepository.findLivrosByPreco(preco);
        mv.addObject("livros", livros);
        return mv;
    }

    @RequestMapping(value = {"/pesquisar","/livros/preco/{preco}"}, method=RequestMethod.POST)
    public ModelAndView pesquisar(@RequestParam("texto") String pesquisar) {
        ModelAndView mv = new ModelAndView("/livros/listarlivros");
        List<Livros> livros = LivrosRepository.findLivrosByTituloLike("%"+pesquisar+"%");
        mv.addObject("livros", livros);
        return mv;
    }

}
