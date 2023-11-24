package com.atividade1.service.serviceImplem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade1.model.Livros;
import com.atividade1.repository.LivrosRepository;
import com.atividade1.service.ServiceLivros;

@Service
public class ServiceImplemLivros implements ServiceLivros{
	
	@Autowired 
	LivrosRepository LivrosRepository;
	
	@Override
	public List<Livros> findAll() {
		// TODO Auto-generated method stub
		return LivrosRepository.findAll();
	}

	@Override
	public Livros findById(int id) {
		// TODO Auto-generated method stub
		return LivrosRepository.findById(id).get();
	}

	@Override
	public Livros save(Livros livros) {
		// TODO Auto-generated method stub
		return LivrosRepository.save(livros);
	}

	@Override
	public List<Livros> findLivrosByPreco(double preco) {
		return LivrosRepository.findLivrosByPreco(preco);
	}

	@Override
	public List<Livros> findLivrosByTituloLike(String titulo) {
		return LivrosRepository.findLivrosByTituloLike(titulo);
	}
}
