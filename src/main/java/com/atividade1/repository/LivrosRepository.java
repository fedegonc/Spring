package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Livros;

public interface LivrosRepository extends JpaRepository<Livros, Integer>{

    void findAllById(Long id);
}
