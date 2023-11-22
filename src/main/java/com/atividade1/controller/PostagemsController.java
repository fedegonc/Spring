package com.atividade1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.Postagem;
import com.atividade1.repository.PostagemsRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;



@Controller
@RequestMapping("/postagem")
public class PostagemsController {

    @Autowired
    private PostagemsRepository postagemRepository;

    @RequestMapping(value = "/novapostagem", method = RequestMethod.GET)
    public String novaPostagem() {
        return "postagem/novo";
    }

    @RequestMapping(value = "/novapostagem", method = RequestMethod.POST)
    public String novaPostagemCadastrada(@Valid Postagem postagem,
                                         BindingResult result, RedirectAttributes msg, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro",
                    "Erro ao cadastrar postagem. Por favor, preencha todos os campos");
            return "redirect:/postagem/novo";
        }
        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                postagem.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar imagem");
        }

        postagemRepository.save(postagem);
        msg.addFlashAttribute("sucesso",
                "Postagem cadastrada.");
        return "redirect:/inicio";
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public ModelAndView getPostagens() {
        ModelAndView mv = new ModelAndView("/postagem/listar");
        List<Postagem> postagens = postagemRepository.findAll();
        mv.addObject("postagens", postagens);
        return mv;
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editarPostagem(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("postagem/editar");
        Optional<Postagem> postagem = postagemRepository.findById(id);
        mv.addObject("titulo", postagem.get().getTitulo());
        mv.addObject("conteudo", postagem.get().getConteudo());
        mv.addObject("id", postagem.get().getId());
        return mv;
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
    public String editarPostagemBanco(Postagem postagem, RedirectAttributes msg) {
        Postagem postagemExistente = postagemRepository.findById(postagem.getId()).orElse(null);
        postagemExistente.setTitulo(postagem.getTitulo());
        postagemExistente.setConteudo(postagem.getConteudo());
        postagemRepository.save(postagemExistente);
        return "redirect:/postagem/listar";
    }

    @RequestMapping(value = "/deletar/{id}", method = RequestMethod.GET)
    public String excluirPostagem(@PathVariable("id") int id) {
        postagemRepository.deleteById(id);
        return "redirect:/postagem/listar";
    }

    @RequestMapping(value = "/imagem/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagensPostagem(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File("./src/main/resources/static/img/" + imagem);
        String imagem2 = imagem;
		if (imagem != null || imagem2.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }
}

