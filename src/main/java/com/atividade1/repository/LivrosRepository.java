package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Livros;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livros, Integer>{
    void findAllById(Long id);

    List<Livros> findLivrosByPreco(double preco);

    List<Livros> findLivrosByTituloLike(String titulo);
}
