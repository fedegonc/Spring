package com.atividade1.controller;

import com.atividade1.model.Alimentos;
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
    private PostagemsRepository postagemRepository;

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

        postagemRepository.save(postagems);
        msg.addFlashAttribute("sucesso",
                "Postagem cadastrada.");
        return "redirect:/listarpostagems";
    }

    @RequestMapping(value = "/listarpostagems", method = RequestMethod.GET)
    public ModelAndView getPostagems() {
        ModelAndView mv = new ModelAndView("postagems/listarpostagems");
        List<Postagems> postagems = postagemRepository.findAll();
        mv.addObject("postagems", postagems);
        return mv;
    }


    @RequestMapping(value = "/editarpostagem/{id}", method = RequestMethod.GET)
    public ModelAndView editarAlimentos(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("postagems/editarpostagem");
        Optional<Postagems> postagemOptional = postagemRepository.findById(id);

        if (postagemOptional.isPresent()) {
            Postagems postagem = postagemOptional.get();
            mv.addObject("postagem", postagem);
        } else {
            mv.setViewName("redirect:/error");
        }
        return mv;
    }


    @RequestMapping(value = "/editarpostagem/{id}", method = RequestMethod.POST)
    public String editarPostagemBanco(@ModelAttribute("postagem") @Valid Postagems postagems,
                                      BindingResult result, RedirectAttributes msg,
                                      @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao editar." +
                    " Por favor, preencha todos os campos");
            return "redirect:/editar/" + postagems.getId();
        }
        Postagems postagemExistente = postagemRepository.findById(postagems.getId()).orElse(null);
        if (postagemExistente != null) {
            postagemExistente.setTitulo(postagems.getTitulo());
            postagemExistente.setConteudo(postagems.getConteudo());
            postagemExistente.setId(postagems.getId());
            try {
                if (!imagem.isEmpty()) {
                    byte[] bytes = imagem.getBytes();
                    Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                    Files.write(caminho, bytes);
                    postagemExistente.setImagem(imagem.getOriginalFilename());
                }
            } catch (IOException e) {
                System.out.println("Error de imagen");
            }

            postagemRepository.save(postagemExistente);
            msg.addFlashAttribute("sucesso", "Alimento editado com sucesso.");
        }

        return "redirect:/listarpostagems";
    }



    @RequestMapping(value = "/imagempostagem/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagensPostagem(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File("./src/main/resources/static/img/" + imagem);
        if (imagem != null && caminho.exists()) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }
    @RequestMapping(value = "/deletarpostagem/{id}", method = RequestMethod.GET)
    public String excluirPostagem(@PathVariable("id") int id) {
        postagemRepository.deleteById(id);
        return "redirect:/listarpostagems";
    }
}

