package com.atividade1.controller;

import com.atividade1.model.Livros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.Alimentos;
import com.atividade1.repository.AlimentosRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class AlimentosController {

    @Autowired
    AlimentosRepository alimentosRepository;

    @RequestMapping(value = "/inicioalimentos", method = RequestMethod.GET)
    public String inicioAlimentos() {
        return "alimentos/inicioalimentos";
    }

    @RequestMapping(value = "/novoalimento", method = RequestMethod.GET)
    public String novoAlimento() {
        return "alimentos/novoalimento";
    }


    @RequestMapping(value = "/novoalimento", method = RequestMethod.POST)
    public String novoAlimentoCadastrado(@Valid Alimentos alimento,
                                         BindingResult result, RedirectAttributes msg,
                                         @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro",
                    "Erro ao cadastrar alimento. Por favor, preencha todos os campos");
            return "redirect:/novoalimento";
        }
        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                alimento.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar imagem");
        }

        alimentosRepository.save(alimento);
        msg.addFlashAttribute("sucesso",
                "Alimento cadastrado.");
        return "redirect:/listaralimentos";
    }

    @RequestMapping(value = "/listaralimentos", method = RequestMethod.GET)
    public ModelAndView getAlimentos() {
        ModelAndView mv = new ModelAndView("alimentos/listaralimentos");
        List<Alimentos> alimentos = alimentosRepository.findAll();
        mv.addObject("alimentos", alimentos);
        return mv;
    }



    @RequestMapping(value = "/editaralimento/{id}", method = RequestMethod.GET)
    public ModelAndView editarAlimentos(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("alimentos/editaralimento");
        Optional<Alimentos> alimentoOptional = alimentosRepository.findById(id);

        if (alimentoOptional.isPresent()) {
            Alimentos alimento = alimentoOptional.get();
            mv.addObject("alimento", alimento);
        } else {
            mv.setViewName("redirect:/error");
        }
        return mv;
    }


    @RequestMapping(value = "/editaralimento/{id}", method = RequestMethod.POST)
    public String editarAlimentoBanco(@ModelAttribute("alimento") @Valid Alimentos alimentos,
                                   BindingResult result, RedirectAttributes msg,
                                   @RequestParam("file") MultipartFile imagem) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao editar." +
                    " Por favor, preencha todos os campos");
            return "redirect:/editar/" + alimentos.getId();
        }
        Alimentos alimentoExistente = alimentosRepository.findById(alimentos.getId()).orElse(null);
        if (alimentoExistente != null) {
            alimentoExistente.setNome(alimentos.getNome());
            alimentoExistente.setPreco(alimentos.getPreco());
            alimentoExistente.setId(alimentos.getId());
            try {
                if (!imagem.isEmpty()) {
                    byte[] bytes = imagem.getBytes();
                    Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                    Files.write(caminho, bytes);
                    alimentoExistente.setImagem(imagem.getOriginalFilename());
                }
            } catch (IOException e) {
                System.out.println("Error de imagen");
            }

            alimentosRepository.save(alimentoExistente);
            msg.addFlashAttribute("sucesso", "Alimento editado com sucesso.");
        }

        return "redirect:/listaralimentos";
    }

    @RequestMapping(value = "/imagemalimento/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagensAlimento(@PathVariable("imagem") String imagem) throws IOException {
        Path caminho = Paths.get("./src/main/resources/static/img/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(caminho);
        }
        return null;
    }
    @RequestMapping(value = "/deletaralimento/{id}", method = RequestMethod.GET)
    public String excluirAlimento(@PathVariable("id") int id) {
        alimentosRepository.deleteById(id);
        return "redirect:/listaralimentos";
    }
}
