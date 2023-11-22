package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Alimento;

public interface AlimentosRepository extends JpaRepository<Alimento, Integer> {
    void findAllById(Long id);
}
