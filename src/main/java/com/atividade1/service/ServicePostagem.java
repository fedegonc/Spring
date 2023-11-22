package com.atividade1.service;

import java.util.List;

import com.atividade1.model.Postagems;

public interface ServicePostagem {

    List<Postagems> findAll();

    Postagems findById(int id);

    Postagems save(Postagems postagem);
}

