package com.atividade1.service;

import java.util.List;

import com.atividade1.model.Postagem;

public interface ServicePostagem {

    List<Postagem> findAll();

    Postagem findById(int id);

    Postagem save(Postagem postagem);
}

