package com.atividade1.service;

import java.util.List;

import com.atividade1.model.Livros;


public interface ServiceLivros {
	List<Livros> findAll();
	Livros findById(int id);
	Livros save(Livros livros);

	List<Livros> findLivrosByPreco(double preco);
}
