package com.atividade1.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atividade1.model.Livros;
import com.atividade1.repository.LivrosRepository;

@Controller
public class LivrosController {
	@Autowired 
	LivrosRepository LivrosRepository;
	
	@RequestMapping(value="/novolivro", method=RequestMethod.GET)
	public String novolivro() {
		return "livros/novolivro";
	}
	
	@RequestMapping(value="/novolivro", method=RequestMethod.POST)
	public String novolivroCadastrado(@Valid Livros livros,
                                      BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile imagem) throws IOException {
			if(result.hasErrors()) {
				msg.addFlashAttribute("erro", 
						"Erro ao cadastrar. Por favor, preencha todos os campos");
				return "redirect:/novolivro";
			}
            try {
                if(!imagem.isEmpty())
            }catch (IOException e)
            {
                System.out.println("error de imagen");
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get(imagem.getOriginalFilename());
                livros.setImagem(imagem.getOriginalFilename());
            }
			LivrosRepository.save(livros);
			msg.addFlashAttribute("sucesso", 
					"Livro cadastrado.");
			return "redirect:/inicio";	
	}
	
	@RequestMapping(value="/listarlivros", method=RequestMethod.GET)
	public ModelAndView getUsuarios() {
		ModelAndView mv = new ModelAndView("/livros/listarlivros");
		List<Livros> livros = LivrosRepository.findAll();
		mv.addObject("livros", livros);
		return mv;
	}

	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") int id){
	ModelAndView mv = new ModelAndView("livros/editarlivro");
	Optional<Livros> livro = LivrosRepository.findById(id);
	mv.addObject("titulo", livro.get().getTitulo());

		mv.addObject("autor", livro.get().getAutor());

		mv.addObject("resumo", livro.get().getResumo());

		mv.addObject("preco", livro.get().getPreco());
		mv.addObject("genero", livro.get().getGenero());
		mv.addObject("id", livro.get().getId());

	return mv;
	}

	@RequestMapping(value="/editar/{id}", method=RequestMethod.POST)
	public String editarLivroBanco(Livros livro, RedirectAttributes msg){
		Livros livroExistente = LivrosRepository.findById(livro.getId()).orElse(null);
		livroExistente.setAutor(livro.getAutor());
		livroExistente.setTitulo(livro.getTitulo());
		livroExistente.setResumo(livro.getResumo());
		livroExistente.setPreco(livro.getPreco());
		livroExistente.setGenero(livro.getGenero());
		LivrosRepository.save(livroExistente);
		return "redirect:/listarlivros";
	}

	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public String excluir(@PathVariable("id") int id){
		LivrosRepository.deleteById(id);
		return "redirect:/listarlivros";
	}

}
