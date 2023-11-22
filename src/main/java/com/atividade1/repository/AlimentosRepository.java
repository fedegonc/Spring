package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Alimentos;

public interface AlimentosRepository extends JpaRepository<Alimentos, Integer> {
    void findAllById(Long id);
}
