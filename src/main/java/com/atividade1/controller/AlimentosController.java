package com.atividade1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.Alimento;
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

    @RequestMapping(value = "/novoalimento", method = RequestMethod.GET)
    public String novoAlimento() {
        return "alimentos/novoalimento";
    }

    @RequestMapping(value = "/novoalimento", method = RequestMethod.POST)
    public String novoAlimentoCadastrado(@Valid Alimento alimento,
                                         BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile imagem) throws IOException {
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
        return "redirect:/inicio";
    }

    @RequestMapping(value = "/listaralimento", method = RequestMethod.GET)
    public ModelAndView getAlimentos() {
        ModelAndView mv = new ModelAndView("alimentos/listaralimento");
        List<Alimento> alimentos = alimentosRepository.findAll();
        mv.addObject("alimentos", alimentos);
        return mv;
    }

    @RequestMapping(value = "/alimento/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editarAlimento(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("alimentos/editaralimento");
        Optional<Alimento> alimento = alimentosRepository.findById(id);
        mv.addObject("nome", alimento.get().getNome());
        mv.addObject("preco", alimento.get().getPreco());
        mv.addObject("id", alimento.get().getId());
        return mv;
    }

    @RequestMapping(value = "/alimento/editar/{id}", method = RequestMethod.POST)
    public String editarAlimentoBanco(Alimento alimento, RedirectAttributes msg) {
        Alimento alimentoExistente = alimentosRepository.findById(alimento.getId()).orElse(null);
        alimentoExistente.setNome(alimento.getNome());
        alimentoExistente.setPreco(alimento.getPreco());
        alimentosRepository.save(alimentoExistente);
        return "redirect:/alimento/listaralimento";
    }

    @RequestMapping(value = "/alimento/excluir/{id}", method = RequestMethod.GET)
    public String excluirAlimento(@PathVariable("id") int id) {
        alimentosRepository.deleteById(id);
        return "redirect:/alimento/listaralimento";
    }

    @RequestMapping(value = "/alimento/imagem/{imagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImagensAlimento(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File("./src/main/resources/static/img/" + imagem);
        String imagem2 = imagem;
		if (imagem != null || imagem2.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }

}
