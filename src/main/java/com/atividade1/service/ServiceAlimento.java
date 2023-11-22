package com.atividade1.service;

import java.util.List;

import com.atividade1.model.Alimento;

public interface ServiceAlimento {

    List<Alimento> findAll();

    Alimento findById(int id);
   
	Alimento save(Alimento alimento);
}
