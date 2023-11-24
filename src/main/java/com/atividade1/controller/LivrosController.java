package com.atividade1.controller;

import com.atividade1.model.Livros;
import com.atividade1.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class LivrosController {
	@Autowired
	LivrosRepository LivrosRepository;

	@RequestMapping(value = "/iniciolivros", method = RequestMethod.GET)
	public String inicioLivros() {
		return "livros/iniciolivros";
	}

	@RequestMapping(value = "/novolivro", method = RequestMethod.GET)
	public String novoLivro() {
		return "livros/novolivro";
	}



	@RequestMapping(value = "/novolivro", method = RequestMethod.POST)
	public String novolivroCadastrado(@Valid Livros livros,
									  BindingResult result, RedirectAttributes msg,
									  @RequestParam("file") MultipartFile imagem) {
		if (result.hasErrors()) {
			msg.addFlashAttribute("erro",
					"Erro ao cadastrar. Por favor, preencha todos os campos");
			return "redirect:/novolivro";
		}
		try {
			if (!imagem.isEmpty()) {
				byte[] bytes = imagem.getBytes();
				Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
				Files.write(caminho, bytes);
				livros.setImagem(imagem.getOriginalFilename());
			}
		} catch (IOException e) {
			System.out.println("error de imagen");
		}

		LivrosRepository.save(livros);
		msg.addFlashAttribute("sucesso",
				"Livro cadastrado.");

		return "redirect:/listarlivros";
	}

	@RequestMapping(value = "/listarlivros", method = RequestMethod.GET)
	public ModelAndView getLivro() {
		ModelAndView mv = new ModelAndView("/livros/listarlivros");
		List<Livros> livros = LivrosRepository.findAll();
		mv.addObject("livros", livros);
		return mv;
	}




	@RequestMapping(value = "/editarlivro/{id}", method = RequestMethod.GET)
	public ModelAndView editarLivro(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("livros/editarlivro");
		Optional<Livros> livroOptional = LivrosRepository.findById(id);
		if (livroOptional.isPresent()) {
			Livros livro = livroOptional.get();
			mv.addObject("livro", livro);
		} else {
			mv.setViewName("redirect:/error"); // Redirigir a la página de error
		}
		return mv;
	}

	@RequestMapping(value = "/editarlivro/{id}", method = RequestMethod.POST)
	public String editarLivroBanco(@ModelAttribute("livro") @Valid Livros livro,
								   BindingResult result, RedirectAttributes msg,
								   @RequestParam("file") MultipartFile imagem) {
		if (result.hasErrors()) {
			msg.addFlashAttribute("erro", "Erro ao editar. " +
					"Por favor, preencha todos os campos");
			return "redirect:/editar/" + livro.getId();
		}
		Livros livroExistente = LivrosRepository.findById(livro.getId()).orElse(null);
		if (livroExistente != null) {
			livroExistente.setAutor(livro.getAutor());
			livroExistente.setTitulo(livro.getTitulo());
			livroExistente.setResumo(livro.getResumo());
			livroExistente.setPreco(livro.getPreco());
			livroExistente.setGenero(livro.getGenero());
			try {
				if (!imagem.isEmpty()) {
					byte[] bytes = imagem.getBytes();
					Path caminho = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
					Files.write(caminho, bytes);
					livroExistente.setImagem(imagem.getOriginalFilename());
				}
			} catch (IOException e) {
				System.out.println("Error de imagen");
			}

			LivrosRepository.save(livroExistente);
			msg.addFlashAttribute("sucesso", "Livro editado com sucesso.");
		}

		return "redirect:/listarlivros";
	}


	@RequestMapping(value = "/imagem/{imagem}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getImagens(@PathVariable("imagem") String imagem) throws IOException {
		File caminho = new File("./src/main/resources/static/img/" + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(caminho.toPath());
		}
		return null;
	}
	@RequestMapping(value = "/deletarlivro/{id}", method = RequestMethod.GET)
	public String excluirLivro(@PathVariable("id") int id) {
		LivrosRepository.deleteById(id);
		return "redirect:/listarlivros";
	}

}
