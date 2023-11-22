package com.atividade1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.atividade1.model.Postagems;
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
public class PostagemsController {

    @Autowired
    private PostagemsRepository PostagemRepository;

    @RequestMapping(value = "/iniciopostagems", method = RequestMethod.GET)
    public String inicioPostagems() {
        return "postagems/iniciopostagems";
    }

    @RequestMapping(value = "/novapostagem", method = RequestMethod.GET)
    public String novaPostagem() {
        return "postagems/novapostagem";
    }




    @RequestMapping(value = "/novapostagem", method = RequestMethod.POST)
    public String novaPostagemCadastrada(@Valid Postagems postagems,
                                         BindingResult result, RedirectAttributes msg,
                                         @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro",
                    "Erro ao cadastrar postagem. Por favor, preencha todos os campos");
            return "redirect:/novapostagem";
        }
        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                postagems.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar imagem");
        }

        PostagemRepository.save(postagems);
        msg.addFlashAttribute("sucesso",
                "Postagem cadastrada.");
        return "redirect:/listarpostagems";
    }

    @RequestMapping(value = "/listarpostagems", method = RequestMethod.GET)
    public ModelAndView getPostagems() {
        ModelAndView mv = new ModelAndView("postagems/listarpostagems");
        List<Postagems> postagems = PostagemRepository.findAll();
        mv.addObject("postagems", postagems);
        return mv;
    }

    @RequestMapping(value = "/editarpostagem/{id}", method = RequestMethod.GET)
    public ModelAndView editarPostagem(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("postagems/editarpostagem");
        Optional<Postagems> postagem = PostagemRepository.findById(id);
        mv.addObject("titulo", postagem.get().getTitulo());
        mv.addObject("conteudo", postagem.get().getConteudo());
        mv.addObject("id", postagem.get().getId());
        return mv;
    }

    @RequestMapping(value = "/editarpostagem/{id}", method = RequestMethod.POST)
    public String editarPostagemBanco(Postagems postagem, RedirectAttributes msg) {
        Postagems postagemExistente = PostagemRepository.findById(postagem.getId()).orElse(null);
        postagemExistente.setTitulo(postagem.getTitulo());
        postagemExistente.setConteudo(postagem.getConteudo());
        PostagemRepository.save(postagemExistente);
        return "redirect:/postagems/listarpostagem";
    }

    @RequestMapping(value = "/deletarpostagem/{id}", method = RequestMethod.GET)
    public String excluirPostagem(@PathVariable("id") int id) {
        PostagemRepository.deleteById(id);
        return "redirect:/postagems/listarpostagem";
    }

    @RequestMapping(value = "/postagem/imagem/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagensPostagem(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File("./src/main/resources/static/img/" + imagem);
        if (imagem != null && caminho.exists()) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }
}

