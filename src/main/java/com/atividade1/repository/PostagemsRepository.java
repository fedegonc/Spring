package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Postagem;

public interface PostagemsRepository extends JpaRepository<Postagem, Integer> {
    void findAllById(Long id);

}
