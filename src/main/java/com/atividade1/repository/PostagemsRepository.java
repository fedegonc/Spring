package com.atividade1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade1.model.Postagems;

public interface PostagemsRepository extends JpaRepository<Postagems, Integer> {
    void findAllById(Long id);

}
